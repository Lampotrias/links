package com.lampotrias.links.ui.list.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.lampotrias.links.R

class SwipeToDeleteCallback(
	context: Context,
	private val onSwipe: (Int) -> Unit
) : ItemTouchHelper.Callback() {

	private val icon = ContextCompat.getDrawable(context, R.drawable.baseline_delete_24)!!.apply {
		colorFilter = PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
	}
	private lateinit var mView: View

	private val redPaint = Paint().apply {
		color = Color.parseColor("#FF3B32")
	}

	override fun getMovementFlags(
		recyclerView: RecyclerView,
		viewHolder: RecyclerView.ViewHolder
	): Int {
		mView = viewHolder.itemView

		return makeMovementFlags(0, ItemTouchHelper.LEFT)
	}

	override fun onMove(
		recyclerView: RecyclerView,
		viewHolder: RecyclerView.ViewHolder,
		target: RecyclerView.ViewHolder
	): Boolean {
		return true
	}

	override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
		val position = viewHolder.adapterPosition
		onSwipe.invoke(position)
	}

	override fun onChildDraw(
		c: Canvas,
		recyclerView: RecyclerView,
		viewHolder: RecyclerView.ViewHolder,
		dX: Float,
		dY: Float,
		actionState: Int,
		isCurrentlyActive: Boolean
	) {
		super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

		val iconVerticalMargin = (mView.height - icon.intrinsicHeight) / 2
		val iconEndMargin = icon.intrinsicHeight
		val iconTop = mView.top + iconVerticalMargin
		val iconBottom = iconTop + icon.intrinsicHeight

		if (dX > 0) { // Swiping to the right
			val iconLeft = mView.left + iconEndMargin + icon.intrinsicWidth
			val iconRight = mView.left + iconEndMargin
			icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

		} else if (dX < 0) { // Swiping to the left
			// Now only this way !!!
			val background = Rect(mView.right + dX.toInt(), mView.top, mView.right, mView.bottom)
			c.drawRect(background, redPaint)

			val iconLeft = mView.right - iconEndMargin - icon.intrinsicWidth
			val iconRight = mView.right - iconEndMargin

			icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
		}


		icon.draw(c)
	}
}