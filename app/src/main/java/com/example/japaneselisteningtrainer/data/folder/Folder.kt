package com.example.japaneselisteningtrainer.data.folder

data class Folder (
    val id : Int,
    val name: String,
    val description: String,
    val createdAt: String,
)

val NullFolder  = Folder(-1, "", "", "")