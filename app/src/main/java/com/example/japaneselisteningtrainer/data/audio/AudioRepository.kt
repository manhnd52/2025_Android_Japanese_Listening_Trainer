package com.example.japaneselisteningtrainer.data.audio

import kotlinx.coroutines.flow.Flow

interface AudioRepository {
    suspend fun add(audio: Audio)
    suspend fun delete(audio: Audio)
    suspend fun update(audio: Audio)
    fun getAllAudioStream(): Flow<List<Audio>>
    fun getAudioStream(id: Int): Flow<Audio?>
}
