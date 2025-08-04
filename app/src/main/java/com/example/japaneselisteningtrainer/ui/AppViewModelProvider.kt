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

package com.example.japaneselisteningtrainer.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.japaneselisteningtrainer.ui.home.HomeViewModel
import com.example.japaneselisteningtrainer.TrainerApplication
import com.example.japaneselisteningtrainer.ui.audio.entry.AudioEntryViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(
                trainerApplication().container.audioRepositoty
            )
        }

        // Initializer for AudioEntryViewModel
        initializer {
            AudioEntryViewModel(
                trainerApplication().container.audioRepositoty
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [TrainerApplication].
 */
fun CreationExtras.trainerApplication(): TrainerApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as TrainerApplication)
