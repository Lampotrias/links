package com.lampotrias.links.data.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.lampotrias.links.domain.cases.AddLinkUseCase
import com.lampotrias.links.domain.cases.GetFoldersUseCase
import com.lampotrias.links.domain.cases.GetLinkMetadataUseCase
import com.lampotrias.links.domain.model.LinkSaveModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber

@HiltWorker
class MetadataCreateWorker @AssistedInject constructor(
	@Assisted private val context: Context,
	@Assisted params: WorkerParameters,
	private val addLinkUseCase: AddLinkUseCase,
	private val getFoldersUseCase: GetFoldersUseCase,
	private val getLinkMetadataUseCase: GetLinkMetadataUseCase,
) : CoroutineWorker(context, params) {
	init {
		Timber.w("init DownloadWorker")
	}

	override suspend fun getForegroundInfo(): ForegroundInfo {
		return ForegroundInfo(
			NOTIFICATION_ID,
			WorkManagerNotification.create(context, "Creating link ...")
		)
	}

	override suspend fun doWork(): Result {
		Timber.w("start doWork")
		inputData.getString(URL_KEY)?.let { url ->
			Timber.w("start doWork, url: $url")

			getFoldersUseCase.invoke().firstOrNull()?.firstOrNull()?.let { defaultFolder ->
				val linkMetadata = getLinkMetadataUseCase.invoke(url)
				Timber.w("start doWork, response: ${linkMetadata.isSuccess}")
				linkMetadata.getOrNull()?.let { remoteLink ->
					addLinkUseCase.invoke(
						LinkSaveModel(
							id = 0L,
							title = remoteLink.title,
							description = remoteLink.description,
							imageUrl = remoteLink.imageUrl,
							dateCreate = System.currentTimeMillis(),
							url = url,
							isFavorite = false,
							folderId = defaultFolder.id,
						)
					)
				}
			}
		}
		delay(20_000)
		Timber.w("end doWork")
		return Result.success()
	}

	companion object {
		const val URL_KEY = "URL_KEY"
		private const val NOTIFICATION_ID = 1111
	}
}