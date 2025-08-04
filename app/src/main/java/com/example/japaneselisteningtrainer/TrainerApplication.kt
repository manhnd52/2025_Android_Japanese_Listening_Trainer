package com.example.japaneselisteningtrainer

import android.app.Application
import com.example.japaneselisteningtrainer.data.AppContainer
import com.example.japaneselisteningtrainer.data.AppDataContainer

class TrainerApplication : Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}