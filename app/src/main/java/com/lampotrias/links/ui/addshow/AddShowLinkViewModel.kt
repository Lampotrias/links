package com.lampotrias.links.ui.addshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.links.domain.cases.AddLinkUseCase
import com.lampotrias.links.domain.cases.GetFoldersUseCase
import com.lampotrias.links.domain.cases.GetLinkMetadataUseCase
import com.lampotrias.links.domain.model.LinkModel
import com.lampotrias.links.domain.model.LinkSaveModel
import com.lampotrias.links.utils.OneShotEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddShowLinkViewModel @Inject constructor(
	private val addLinkUseCase: AddLinkUseCase,
	private val getFoldersUseCase: GetFoldersUseCase,
	private val getLinkMetadataUseCase: GetLinkMetadataUseCase,
) : ViewModel() {

	private val _uiState = MutableStateFlow(AddShodUiState())
	val uiState = _uiState.asStateFlow()

	init {
		viewModelScope.launch {
			_uiState.update {
				it.copy(
					folderModel = getFoldersUseCase.invoke().firstOrNull()?.firstOrNull()
				)
			}
		}
	}

	fun setInitialState(linkModel: LinkModel) {
		_uiState.update {
			it.copy(
				titleLink = linkModel.title,
				descriptionLink = linkModel.description,
				urlLink = linkModel.url,
				imageUrlLink = linkModel.imageUrl,
			)
		}
	}

	fun setInitialUrl(url: String) {
		_uiState.update {
			it.copy(
				urlLink = url,
			)
		}
	}

	fun getMetadata(url: String) {
		_uiState.update {
			it.copy(
				showLoading = true,
				urlLink = url
			)
		}
		viewModelScope.launch {
			getLinkMetadataUseCase.invoke(url).fold(
				{ linkMetadata ->
					_uiState.update {
						it.copy(
							showLoading = false,
							titleLink = linkMetadata.title,
							descriptionLink = linkMetadata.description,
							urlLink = linkMetadata.url,
							imageUrlLink = linkMetadata.imageUrl,
						)
					}
				},
				{ throwable ->
					_uiState.update {
						it.copy(
							error = OneShotEvent(throwable),
							showLoading = false,
							descriptionLink = "",
							urlLink = "",
							imageUrlLink = ""
						)
					}
				}
			)
		}
	}

	fun addLink(linkSaveModel: LinkSaveModel) {
		viewModelScope.launch {
			addLinkUseCase.invoke(linkSaveModel).fold(
				{ newId ->
					_uiState.update {
						it.copy(
							success = OneShotEvent(newId)
						)
					}
				},
				{ throwable ->
					_uiState.update {
						it.copy(
							error = OneShotEvent(throwable)
						)
					}
				}
			)
		}
	}
}