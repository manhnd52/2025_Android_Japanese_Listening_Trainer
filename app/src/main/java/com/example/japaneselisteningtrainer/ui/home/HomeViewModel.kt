package com.example.japaneselisteningtrainer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.japaneselisteningtrainer.data.audio.Audio
import com.example.japaneselisteningtrainer.data.audio.AudioRepository
import com.example.japaneselisteningtrainer.data.audio.MockAudioRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel (audioRepository: AudioRepository) : ViewModel() {
    val uiState: StateFlow<HomeUiState> =
        audioRepository.getAllAudioStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

class HomeUiState(val audioList: List<Audio> = emptyList())