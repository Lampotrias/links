package com.lampotrias.links.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.links.domain.cases.DeleteLinkUseCase
import com.lampotrias.links.domain.cases.GetLinksUseCase
import com.lampotrias.links.domain.cases.RestoreLinkUseCase
import com.lampotrias.links.domain.cases.UpdateFavoriteLinkUseCase
import com.lampotrias.links.domain.model.LinkModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinksListViewModel @Inject constructor(
	private val getLinksUseCase: GetLinksUseCase,
	private val deleteLinkUseCase: DeleteLinkUseCase,
	private val restoreLinkUseCase: RestoreLinkUseCase,
	private val updateFavoriteLinkUseCase: UpdateFavoriteLinkUseCase,
) : ViewModel() {


	private val _uiState = MutableStateFlow(LinksListUiState())
	val uiState = _uiState.asStateFlow()

	init {
		viewModelScope.launch {
			getLinksUseCase.invoke().collect { links ->
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