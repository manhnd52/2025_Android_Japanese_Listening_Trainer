package com.example.japaneselisteningtrainer.ui.audio.entry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.japaneselisteningtrainer.data.audio.Audio
import com.example.japaneselisteningtrainer.data.audio.AudioRepository

class AudioEntryViewModel (
    private val audioRepository: AudioRepository
) : ViewModel() {
    val audioTitle = mutableStateOf("")

    suspend fun addAudio() {
        audioRepository.add(Audio(1, audioTitle.value))
    }
}
