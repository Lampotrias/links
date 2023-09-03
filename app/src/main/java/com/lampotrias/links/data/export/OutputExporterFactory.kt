package com.lampotrias.links.data.export

import com.lampotrias.links.data.export.type.CsvConverter
import com.lampotrias.links.data.export.type.JsonConverter


object OutputExporterFactory {
	fun getExportOutputType(exportOutputType: ExportOutputType): IOutputExporter {
		return when (exportOutputType) {
			ExportJson -> JsonConverter()
			is ExportCsv -> CsvConverter()
		}
	}
}