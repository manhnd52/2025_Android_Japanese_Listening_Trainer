package com.example.japaneselisteningtrainer.data.audio

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockAudioRepository : AudioRepository {
    private val audioDatabase = mutableListOf<Audio>()

    init {
        audioDatabase.add(Audio(1, "Chạy ngay đi"))
        audioDatabase.add(Audio(2, "Trước khi mọi điều dần tồi tệ hơn"))
        audioDatabase.add(Audio(3, "Không còn anh cạnh bên em, tạm biệt một tương lai tăm tối"))
    }

    override suspend fun add(audio: Audio) : Int{
        audioDatabase.add(audio)
        return audioDatabase.indexOf(audio)
    }

    override suspend fun delete(audio: Audio) {
        audioDatabase.remove(audio)
    }

    override suspend fun update(audio: Audio) {
        val index = audioDatabase.indexOfFirst { it.id == audio.id }
        if (index >= 0) {
            audioDatabase[index] = audio
        }
    }
    override fun getAllAudioStream(): Flow<List<Audio>> {
        return flow {
            emit(audioDatabase)
        }
    }

    override fun getAudioStream(id: Int): Flow<Audio?> {
        return flow {
            val audio = audioDatabase.find { it.id == id }
            emit(audio)
        }
    }
}
