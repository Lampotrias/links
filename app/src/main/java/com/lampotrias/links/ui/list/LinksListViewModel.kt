package com.lampotrias.links.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.links.domain.cases.DeleteLinkUseCase
import com.lampotrias.links.domain.cases.GetAllLinksUseCase
import com.lampotrias.links.domain.cases.GetFavoritesLinksUseCase
import com.lampotrias.links.domain.cases.RestoreLinkUseCase
import com.lampotrias.links.domain.cases.UpdateFavoriteLinkUseCase
import com.lampotrias.links.domain.model.LinkModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LinksListViewModel @Inject constructor(
	private val getAllLinksUseCase: GetAllLinksUseCase,
	private val getFavoritesLinksUseCase: GetFavoritesLinksUseCase,
	private val deleteLinkUseCase: DeleteLinkUseCase,
	private val restoreLinkUseCase: RestoreLinkUseCase,
	private val updateFavoriteLinkUseCase: UpdateFavoriteLinkUseCase,
) : ViewModel() {


	private val _uiState = MutableStateFlow(LinksListUiState())
	val uiState = _uiState.asStateFlow()

	init {
		Timber.e("init")
	}

	fun streamAllLinks() {
		viewModelScope.launch {
			getAllLinksUseCase.invoke().collect { links ->
				_uiState.update {
					it.copy(
						links = links
					)
				}
			}
		}
	}

	fun streamFavoriteLinks() {
		viewModelScope.launch {
			getFavoritesLinksUseCase.invoke().collect { links ->
				_uiState.update {
					it.copy(
						links = links
					)
				}
			}
		}
	}

	fun deleteLink(linkModel: LinkModel) {
		viewModelScope.launch {
			deleteLinkUseCase.invoke(linkModel)
		}
	}

	fun restoreLink(linkModel: LinkModel) {
		viewModelScope.launch {
			restoreLinkUseCase.invoke(linkModel)
		}
	}

	fun updateFavorite(linkModel: LinkModel) {
		viewModelScope.launch {
			updateFavoriteLinkUseCase.invoke(linkModel)
		}
	}
}