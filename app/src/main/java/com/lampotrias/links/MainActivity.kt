package com.lampotrias.links

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.lampotrias.links.data.workmanager.MetadataCreateWorker
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		performIntent(intent)
	}

	override fun onNewIntent(intent: Intent?) {
		super.onNewIntent(intent)

		performIntent(intent)
	}

	private fun performIntent(intent: Intent?) {
		if (intent?.action == Intent.ACTION_SEND) {

			supportFragmentManager.popBackStack(null, POP_BACK_STACK_INCLUSIVE)
			val url = intent.getStringExtra(Intent.EXTRA_TEXT)

			if (url?.startsWith("http") == true) {
//				val addEditLinkFragment = AddEditLinkFragment.newInstanceForAdd(url)
//				supportFragmentManager.commit {
//					setReorderingAllowed(true)
//					add(R.id.main_fragment_container, addEditLinkFragment, "add-edit")
//					addToBackStack(null)
//				}

				val builder = Data.Builder()
					.putString(MetadataCreateWorker.URL_KEY, url)
					.build()

				val worker = OneTimeWorkRequestBuilder<MetadataCreateWorker>()
					.setInputData(builder)
					.build()


				WorkManager.getInstance(this)
					.enqueue(worker)

			}
		}
	}
}