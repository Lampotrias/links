package com.lampotrias.links.ui.addedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.links.domain.cases.AddLinkUseCase
import com.lampotrias.links.domain.cases.GetLinkMetadataUseCase
import com.lampotrias.links.domain.model.LinkModel
import com.lampotrias.links.utils.OneShotEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditLinkViewModel @Inject constructor(
	private val addLinkUseCase: AddLinkUseCase,
//	private val edUseCase: AddLinkUseCase,
	private val getLinkMetadataUseCase: GetLinkMetadataUseCase,
) : ViewModel() {

	private val _uiState = MutableStateFlow(AddEditUiState())
	val uiState = _uiState.asStateFlow()

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

	fun addLink(linkModel: LinkModel) {
		viewModelScope.launch {
			addLinkUseCase.invoke(linkModel).fold(
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