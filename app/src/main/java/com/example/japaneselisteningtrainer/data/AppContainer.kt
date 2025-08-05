/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.japaneselisteningtrainer.data

import android.content.Context
import com.example.japaneselisteningtrainer.data.audio.AudioRepository
import com.example.japaneselisteningtrainer.data.audio.LocalAudioRepository
import com.example.japaneselisteningtrainer.data.audio.MockAudioRepository
import com.example.japaneselisteningtrainer.data.folder.FolderRepository
import com.example.japaneselisteningtrainer.data.folder.LocalFolderRepository
import com.example.japaneselisteningtrainer.data.folder.MockFolderRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val audioRepositoty: AudioRepository
    val folderRepository: FolderRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [AudioRepository]
     */
    private val jltDbHelper = JLTDbHelper(context)

    override val audioRepositoty: AudioRepository = LocalAudioRepository(jltDbHelper)
    /**
     * Implementation for [FolderRepository]
     */
    override val folderRepository: FolderRepository = LocalFolderRepository(jltDbHelper)
}
