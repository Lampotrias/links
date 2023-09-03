package com.lampotrias.links.domain.cases

import android.content.Context
import com.lampotrias.links.data.db.LinksStorage
import com.lampotrias.links.data.export.ExportCsv
import com.lampotrias.links.data.export.ExportJson
import com.lampotrias.links.data.export.ExportOutputType
import com.lampotrias.links.data.export.OutputExporterFactory
import com.lampotrias.links.di.DispatcherProvider
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class ExportUseCase @Inject constructor(
	private val context: Context,
	private val linksStorage: LinksStorage,
	private val dispatcherProvider: DispatcherProvider,
) {
	private val exportedDir = File(context.cacheDir, EXPORTED_DIR)

	suspend fun exportJson(): Result<File> {
		return exportInternal(ExportJson)
	}

	suspend fun exportCsv(): Result<File> {
		return exportInternal(ExportCsv())
	}

	private suspend fun exportInternal(type: ExportOutputType): Result<File> {
		return withContext(dispatcherProvider.io) {
			linksStorage.getAllLinks().firstOrNull()?.let { links ->
				val converter = OutputExporterFactory.getExportOutputType(type)
				val content = converter.exportData(links)

				return@withContext writeFile(content, type)
			}

			Result.failure(RuntimeException(""))
		}
	}

	private fun writeFile(content: String, type: ExportOutputType): Result<File> {
		val fileName = "${System.currentTimeMillis()}." + when (type) {
			is ExportCsv -> "csv"
			ExportJson -> "json"
		}
		val targetFile = File(exportedDir, fileName)
		return try {
			if (exportedDir.isDirectory || exportedDir.mkdirs()) {
				targetFile.createNewFile()
				targetFile.writeText(content)
				Result.success(targetFile)
			} else {
				Result.failure(RuntimeException("Error create exported dir"))
			}
		} catch (ex: Exception) {
			Result.failure(ex)
		}
	}

	companion object {
		private const val EXPORTED_DIR = "exported"
	}
}