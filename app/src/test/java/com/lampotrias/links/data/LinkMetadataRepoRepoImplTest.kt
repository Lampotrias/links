package com.lampotrias.links.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.lampotrias.links.domain.LinkMetadataRepo
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import javax.inject.Inject



@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@Config(application = HiltTestApplication::class)
class DetailActivityInjectionTest {

	@get:Rule
	var hiltRule = HiltAndroidRule(this)

	@Inject
	lateinit var linkMetadataRepo: LinkMetadataRepo

	@Before
	fun init() {
		hiltRule.inject()
	}

	@Test
	fun getLinkMetadata() {
		runBlocking {
			val model =
				linkMetadataRepo.getLinkMetadata("https://medium.com/@gustavohenriques/jetpack-compose-mastering-states-3966b87a8fc5")

				assertThat(model.isSuccess).isTrue()
				assertThat(model.getOrNull()?.title).isEqualTo("Jetpack Compose: Mastering States")
		}
	}
}