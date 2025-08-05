package com.example.japaneselisteningtrainer.data

import android.provider.BaseColumns

object JLTContract {
    // Global constants (database name, version)
    const val DATABASE_NAME = "listening_trainer.db"
    const val DATABASE_VERSION = 1

    object Audio : BaseColumns {
        const val TABLE_NAME = "audio" + BaseColumns._ID

        const val COLUMN_FOLDER_ID = "folder_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_FILE_PATH = "file_path"
        const val COLUMN_SCRIPT = "script"
        const val COLUMN_TRANSLATE = "translate"
        const val COLUMN_IS_SUSPENDED = "is_suspended"
        const val COLUMN_IS_FAVORITE = "is_favorite"
        const val COLUMN_LISTEN_TIMES = "listen_times"
        const val COLUMN_CREATED_AT = "created_at"
    }

    object Folder : BaseColumns {
        const val TABLE_NAME = "folder"

        const val COLUMN_NAME = "name"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_CREATED_AT = "created_at"
    }

    const val SQL_CREATE_AUDIO_TABLE = "CREATE TABLE ${Audio.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${Audio.COLUMN_FOLDER_ID} INTEGER," +
            "${Audio.COLUMN_TITLE} TEXT," +
            "${Audio.COLUMN_FILE_PATH} TEXT," +
            "${Audio.COLUMN_SCRIPT} TEXT," +
            "${Audio.COLUMN_TRANSLATE} TEXT," +
            "${Audio.COLUMN_IS_SUSPENDED} INTEGER," +
            "${Audio.COLUMN_IS_FAVORITE} INTEGER," +
            "${Audio.COLUMN_LISTEN_TIMES} INTEGER," +
            "${Audio.COLUMN_CREATED_AT} TEXT," +
            "FOREIGN KEY(${Audio.COLUMN_FOLDER_ID}) REFERENCES ${Folder.TABLE_NAME}(${BaseColumns._ID})" +
            ")"

    const val SQL_CREATE_FOLDER_TABLE = "CREATE TABLE ${Folder.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${Folder.COLUMN_NAME} TEXT," +
            "${Folder.COLUMN_DESCRIPTION} TEXT," +
            "${Folder.COLUMN_CREATED_AT} TEXT" +
            ")"

    const val SQL_DELETE_AUDIO_TABLE = "DROP TABLE IF EXISTS ${Audio.TABLE_NAME}"
    const val SQL_DELETE_FOLDER_TABLE = "DROP TABLE IF EXISTS ${Folder.TABLE_NAME}"
}

