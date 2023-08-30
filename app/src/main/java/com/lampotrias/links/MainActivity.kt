package com.lampotrias.links

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.commit
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.lampotrias.links.data.workmanager.MetadataCreateWorker
import com.lampotrias.links.databinding.ActivityMainBinding
import com.lampotrias.links.ui.list.LinksListFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		val view = binding.root
		setContentView(view)

		performIntent(intent)

		val ss = LinksListFragment.newInstanceForList()
		val ww = LinksListFragment.newInstanceForFavorites()

		binding.bottomNavigation.setOnItemSelectedListener { item ->
			when (item.itemId) {
				R.id.m_list -> {
					openFragment(ss)
					true
				}

				R.id.m_favorites -> {
					openFragment(ww)
					true
				}

				R.id.m_settings -> {
					// Respond to navigation item 2 click
					true
				}

				else -> {
					true
				}
			}
		}
	}

	private fun openFragment(fragment: Fragment) {
		supportFragmentManager.commit {
			replace(R.id.main_fragment_container, fragment)
			addToBackStack(null)
		}
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