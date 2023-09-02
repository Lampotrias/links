package com.lampotrias.links.ui.settings

enum class AppThemeColor(val title: String) {
	System("System"),
	Dark("Dark"),
	Light("Light"), ;

	companion object {
		fun from(value: String?): AppThemeColor {
			return when (value) {
				Dark.title -> Dark
				Light.title -> Light
				else -> System
			}
		}
	}
}