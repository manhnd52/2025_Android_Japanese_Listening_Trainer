package com.example.japaneselisteningtrainer

import androidx.test.core.app.ApplicationProvider
import com.example.japaneselisteningtrainer.data.JLTDbHelper
import com.example.japaneselisteningtrainer.data.audio.Audio
import com.example.japaneselisteningtrainer.data.audio.LocalAudioRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNull
import org.junit.Test

class LocalAudioRepositoryTest {

    private val dbHelper = JLTDbHelper(ApplicationProvider.getApplicationContext())
    private val repo = LocalAudioRepository(dbHelper)
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Test
    fun addAudio() = runTest {
        val audio = Audio(title = "Test Audio Title")
        val id = repo.add(audio)

        val result = repo.getAudioStream(id).first()
        assert(result?.title == audio.title)
    }


    @Test
    fun deleteAudio() = runTest {
        var audio = Audio(
            title = "Test Audio Title"
        )

        val id = repo.add(audio)
        audio = audio.copy(id = id)

        // Get the audio flow from the repository
        val audioFlow = repo.getAudioStream(id)
        assert(audioFlow.first()?.title  == audio.title)

        repo.delete(audio)
        assertNull(audioFlow.first {it == null})
    }

    @Test
    fun updateAudio() = runTest {
        val audio = Audio(
            title = "Test Audio Title"
        )

        val id = repo.add(audio)
        var audioWithId = audio.copy(id = id)
        val audioFlow = repo.getAudioStream(id)

        audioWithId = audioWithId.copy(title = "New Title")
        repo.update(audioWithId)

        val updatedAudio = audioFlow.first()
        assert(updatedAudio?.title == "New Title")
    }
}