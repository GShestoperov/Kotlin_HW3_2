package data

import java.time.LocalDateTime
import java.time.LocalDateTime.now

data class Note(
    val nid: Long,
    val title: String,
    val text: String,
    val dateTime: LocalDateTime = now(),
    val comments: Int = 0,
    val viewUrl: String = "",
    val privacy: Int = 0,
    val canComment: Boolean = true,
    val commentPrivacy: Int = 0,
    val privacyView: String = "", // в спец. формате
    val privacyComment: String = "" // в спец. формате
)

class NoteNotFoundException(message: String) : RuntimeException(message)

data class NoteComment(
    val cid: Long,
    val nid: Long,
    val message: String,
    val dateTime: LocalDateTime = now(),
    var isDeleted: Boolean = false
)

class CommentNotFoundException(message: String) : RuntimeException(message)
