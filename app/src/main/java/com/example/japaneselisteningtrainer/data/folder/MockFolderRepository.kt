package com.example.japaneselisteningtrainer.data.folder

import kotlinx.coroutines.flow.Flow

class MockFolderRepository : FolderRepository {
    override suspend fun add(folder: Folder) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(folder: Folder) {
        TODO("Not yet implemented")
    }

    override suspend fun update(folder: Folder) {
        TODO("Not yet implemented")
    }

    override fun getAllFolderStream(): Flow<List<Folder>> {
        TODO("Not yet implemented")
    }

    override fun getFolderStream(id: Int): Flow<Folder?> {
        TODO("Not yet implemented")
    }

}
