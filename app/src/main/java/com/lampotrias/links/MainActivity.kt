package com.lampotrias.links

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.lampotrias.links.data.workmanager.MetadataCreateWorker
import com.lampotrias.links.databinding.ActivityMainBinding
import com.lampotrias.links.ui.list.LinksListFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.e("onCreate $this")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        performIntent(intent)

        if (savedInstanceState == null) {
			switchFragment(NavigationPosition.List)
        }

        binding.bottomNavigation.menu.getItem(0).isChecked = true

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.m_list -> {
					switchFragment(NavigationPosition.List)
                    true
                }

                R.id.m_favorites -> {
					switchFragment(NavigationPosition.Favorites)
                    true
                }

                R.id.m_settings -> {

                    true
                }

                else -> {
                    true
                }
            }
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

    override fun onDestroy() {
        super.onDestroy()

        Timber.e("onDestroy $this")
    }

	private fun switchFragment(navigationPosition: NavigationPosition): Boolean {
		val targetFragment = findOrCreateFragment(navigationPosition)
		if (targetFragment.isAdded) {
			return false
		}

		supportFragmentManager.commit {
			// Detach a fragment
			supportFragmentManager.findFragmentById(R.id.main_fragment_container)?.also {
				detach(it)
			}
			// Attach or add a fragment
			if (targetFragment.isDetached) {
				attach(targetFragment)
			} else {
				add(R.id.main_fragment_container, targetFragment, navigationPosition.tag)
			}
			// Set the animation for this transaction
			setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
		}
		// Immediately execute transactions
		return supportFragmentManager.executePendingTransactions()
	}

    private fun findOrCreateFragment(navigationPosition: NavigationPosition): Fragment {
        return supportFragmentManager.findFragmentByTag(navigationPosition.tag) ?: run {
            when (navigationPosition) {
                NavigationPosition.Favorites -> LinksListFragment.newInstanceForFavorites()
                NavigationPosition.List -> LinksListFragment.newInstanceForList()
                NavigationPosition.Settings -> LinksListFragment.newInstanceForFavorites()
            }
        }
    }
}