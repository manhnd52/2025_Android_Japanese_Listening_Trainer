package com.example.japaneselisteningtrainer.data.audio

import java.time.Instant.now

data class Audio(
    val id: Int = 0,
    var title: String = "",
    val folderId: Int = 0,
    val filePath: String = "",
    val script: String = "",
    val translate: String = "",
    val isSuspended: Boolean = false,
    val isFavorite: Boolean = false,
    val listenTimes: Int = 0,
    val createdAt: String = ""
) {
    constructor(id: Int, title: String) : this(id,
        title,
        0,
        "",
        "",
        "",
        false,
        false,
        0,
        ""
    )
}