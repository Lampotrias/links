package com.lampotrias.links.data.export


sealed class ExportOutputType

data object ExportJson : ExportOutputType()

class ExportCsv(val separator: String = ";") : ExportOutputType()