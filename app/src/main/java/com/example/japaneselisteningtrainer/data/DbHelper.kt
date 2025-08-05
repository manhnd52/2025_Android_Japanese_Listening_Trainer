package com.example.japaneselisteningtrainer.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.japaneselisteningtrainer.data.JLTContract.DATABASE_NAME
import com.example.japaneselisteningtrainer.data.JLTContract.DATABASE_VERSION
import com.example.japaneselisteningtrainer.data.JLTContract.SQL_CREATE_AUDIO_TABLE
import com.example.japaneselisteningtrainer.data.JLTContract.SQL_CREATE_FOLDER_TABLE
import com.example.japaneselisteningtrainer.data.JLTContract.SQL_DELETE_AUDIO_TABLE
import com.example.japaneselisteningtrainer.data.JLTContract.SQL_DELETE_FOLDER_TABLE


class JLTDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_AUDIO_TABLE)
        db.execSQL(SQL_CREATE_FOLDER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_AUDIO_TABLE)
        db.execSQL(SQL_DELETE_FOLDER_TABLE)
        onCreate(db)
    }
}