package com.lampotrias.links.utils

class OneShotEvent<out T>(private val content: T) {

	var hasBeenHandled = false
		private set

	/**
	 * Returns the content and prevents its use again.
	 */
	fun getContentIfNotHandled(): T? {
		return if (hasBeenHandled) {
			null
		} else {
			hasBeenHandled = true
			content
		}
	}

	/**
	 * Returns the content, even if it's already been handled.
	 */
	@Suppress("unused")
	fun peekContent(): T = content
}