package com.lampotrias.links.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lampotrias.links.domain.cases.GetLinksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinksListViewModel @Inject constructor(
	private val getLinksUseCase: GetLinksUseCase,
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
}