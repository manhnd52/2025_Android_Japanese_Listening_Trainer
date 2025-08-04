package com.example.japaneselisteningtrainer.data.folder

import kotlinx.coroutines.flow.Flow

interface FolderRepository {
    suspend fun add(folder: Folder)
    suspend fun delete(folder: Folder)
    suspend fun update(folder: Folder)
    fun getAllFolderStream(): Flow<List<Folder>>
    fun getFolderStream(id: Int): Flow<Folder?>
}
