package com.example.japaneselisteningtrainer.data.audio

import android.content.ContentValues
import android.provider.BaseColumns
import android.util.Log
import com.example.japaneselisteningtrainer.data.JLTContract
import com.example.japaneselisteningtrainer.data.JLTDbHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext

interface SQLiteChangeObserver {
    fun onChanged()
}

class DbChangeNotifier {
    private val observers = mutableListOf<SQLiteChangeObserver>()

    fun register(observer: SQLiteChangeObserver) {
        observers.add(observer)
    }

    fun remove(observer: SQLiteChangeObserver) {
        observers.remove(observer)
    }

    fun notifyChanged() {
        for (observer in observers) {
            observer.onChanged()
        }
    }
}

class LocalAudioRepository(private val dbHelper: JLTDbHelper) : AudioRepository {
    private val db = dbHelper.writableDatabase
    private val notifier = DbChangeNotifier()

    override suspend fun add(audio: Audio) : Int = withContext(Dispatchers.IO) {
        val values = ContentValues().apply {
            put(JLTContract.Audio.COLUMN_FOLDER_ID, audio.folderId)
            put(JLTContract.Audio.COLUMN_TITLE, audio.title)
            put(JLTContract.Audio.COLUMN_FILE_PATH, audio.filePath)
            put(JLTContract.Audio.COLUMN_SCRIPT, audio.script)
            put(JLTContract.Audio.COLUMN_TRANSLATE, audio.translate)
            put(JLTContract.Audio.COLUMN_IS_SUSPENDED, audio.isSuspended)
            put(JLTContract.Audio.COLUMN_IS_FAVORITE, audio.isFavorite)
            put(JLTContract.Audio.COLUMN_LISTEN_TIMES, audio.listenTimes)
            put(JLTContract.Audio.COLUMN_CREATED_AT, audio.createdAt)
        }
        val id = db.insert(JLTContract.Audio.TABLE_NAME, null, values)
        notifier.notifyChanged()
        return@withContext id.toInt()
    }

    override suspend fun delete(audio: Audio) = withContext(Dispatchers.IO) {
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(audio.id.toString())
        db.delete(JLTContract.Audio.TABLE_NAME, selection, selectionArgs)
        notifier.notifyChanged()
    }

    override suspend fun update(audio: Audio) = withContext(Dispatchers.IO) {
        val values = ContentValues().apply {
            put(JLTContract.Audio.COLUMN_FOLDER_ID, audio.folderId)
            put(JLTContract.Audio.COLUMN_TITLE, audio.title)
            put(JLTContract.Audio.COLUMN_FILE_PATH, audio.filePath)
            put(JLTContract.Audio.COLUMN_SCRIPT, audio.script)
            put(JLTContract.Audio.COLUMN_TRANSLATE, audio.translate)
            put(JLTContract.Audio.COLUMN_IS_SUSPENDED, audio.isSuspended)
            put(JLTContract.Audio.COLUMN_IS_FAVORITE, audio.isFavorite)
            put(JLTContract.Audio.COLUMN_LISTEN_TIMES, audio.listenTimes)
            put(JLTContract.Audio.COLUMN_CREATED_AT, audio.createdAt)
        }
        val selection = "${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(audio.id.toString())
        db.update(JLTContract.Audio.TABLE_NAME, values, selection, selectionArgs)
        notifier.notifyChanged()
    }

    override fun getAllAudioStream(): Flow<List<Audio>> = callbackFlow {

        fun query(): List<Audio> {
            val audioList = mutableListOf<Audio>()
            val query = "SELECT * FROM ${JLTContract.Audio.TABLE_NAME}"
            val cursor = db.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
                    val folderId =
                        cursor.getInt(cursor.getColumnIndexOrThrow(JLTContract.Audio.COLUMN_FOLDER_ID))
                    val title =
                        cursor.getString(cursor.getColumnIndexOrThrow(JLTContract.Audio.COLUMN_TITLE))
                    val filePath =
                        cursor.getString(cursor.getColumnIndexOrThrow(JLTContract.Audio.COLUMN_FILE_PATH))
                    val script =
                        cursor.getString(cursor.getColumnIndexOrThrow(JLTContract.Audio.COLUMN_SCRIPT))
                    val translate =
                        cursor.getString(cursor.getColumnIndexOrThrow(JLTContract.Audio.COLUMN_TRANSLATE))
                    val isSuspended =
                        (cursor.getInt(cursor.getColumnIndexOrThrow(JLTContract.Audio.COLUMN_IS_SUSPENDED)) == 1)
                    val isFavorite =
                        (cursor.getInt(cursor.getColumnIndexOrThrow(JLTContract.Audio.COLUMN_IS_FAVORITE)) == 1)
                    val listenTimes =
                        cursor.getInt(cursor.getColumnIndexOrThrow(JLTContract.Audio.COLUMN_LISTEN_TIMES))
                    val createdAt =
                        cursor.getString(cursor.getColumnIndexOrThrow(JLTContract.Audio.COLUMN_CREATED_AT))
                    val audio = Audio(
                        id,
                        title,
                        folderId,
                        filePath,
                        script,
                        translate,
                        isSuspended,
                        isFavorite,
                        listenTimes,
                        createdAt
                    )
                    audioList.add(audio)
                } while (cursor.moveToNext())
            }
            cursor.close()
            return audioList
        }

        val observer = object : SQLiteChangeObserver {
            override fun onChanged() {
                try {
                    trySend(query()).isSuccess
                } catch (e: Exception) {
                    Log.v("MyTag", e.toString())
                }
            }
        }

        notifier.register(observer)
        trySend(query()).isSuccess

        // Hủy đăng ký khi Flow bị cancel
        awaitClose {
            notifier.remove(observer)
        }
    }

    override fun getAudioStream(id: Int): Flow<Audio?> = callbackFlow {
        fun query(): Audio? {
            val query = "SELECT * FROM ${JLTContract.Audio.TABLE_NAME} WHERE ${BaseColumns._ID} = ?"
            val cursor = db.rawQuery(query, arrayOf(id.toString()))
            if (cursor.moveToFirst()) {
                val folderId =
                    cursor.getInt(cursor.getColumnIndexOrThrow(JLTContract.Audio.COLUMN_FOLDER_ID))
                val title =
                    cursor.getString(cursor.getColumnIndexOrThrow(JLTContract.Audio.COLUMN_TITLE))
                val filePath =
                    cursor.getString(cursor.getColumnIndexOrThrow(JLTContract.Audio.COLUMN_FILE_PATH))
                val script =
                    cursor.getString(cursor.getColumnIndexOrThrow(JLTContract.Audio.COLUMN_SCRIPT))
                val translate =
                    cursor.getString(cursor.getColumnIndexOrThrow(JLTContract.Audio.COLUMN_TRANSLATE))
                val isSuspended =
                    (cursor.getInt(cursor.getColumnIndexOrThrow(JLTContract.Audio.COLUMN_IS_SUSPENDED)) == 1)
                val isFavorite =
                    (cursor.getInt(cursor.getColumnIndexOrThrow(JLTContract.Audio.COLUMN_IS_FAVORITE)) == 1)
                val listenTimes =
                    cursor.getInt(cursor.getColumnIndexOrThrow(JLTContract.Audio.COLUMN_LISTEN_TIMES))
                val createdAt =
                    cursor.getString(cursor.getColumnIndexOrThrow(JLTContract.Audio.COLUMN_CREATED_AT))
                cursor.close()
                return Audio(
                    id,
                    title,
                    folderId,
                    filePath,
                    script,
                    translate,
                    isSuspended,
                    isFavorite,
                    listenTimes,
                    createdAt
                )
            }
            return null
        }

        val observer = object : SQLiteChangeObserver {
            override fun onChanged() {
                trySend(query()).isSuccess
            }
        }

        notifier.register(observer)
        trySend(query()).isSuccess

        awaitClose {
            notifier.remove(observer)
        }
    }
}