package data

interface Attachment {
    val type: String
}

data class Photo(
    val id: Long,
    val albumId: Long? = null,
    val ownerId: Long,
    val userId: Long? = null,
    val text: String = "",
    val date: Long,
    val url: String
) {
}

data class PhotoAttachment(
    val photo: Photo
) : Attachment  {
    override val type = "photo"
}

data class Video(
    val id: Long,
    val ownerId: Long,
    val title: String = "",
    val description: String = "",
    val date: Long,
    val url: String
) {
}

data class VideoAttachment(
    val video: Video
) : Attachment  {
    override val type = "video"
}

data class Audio(
    val id: Long,
    val ownerId: Long,
    val artist: String = "",
    val title: String = "",
    val date: Long,
    val url: String
) {
}

data class AudioAttachment(
    val audio: Audio
) : Attachment  {
    override val type = "audio"
}

data class Document(
    val id: Long,
    val ownerId: Long,
    val title: String = "",
    val docType: Int,
    val date: Long,
    val url: String
) {
}

data class DocumentAttachment(
    val document: Document
) : Attachment  {
    override val type = "document"
}

data class Link(
    val url: String,
    val title: String = "",
    val caption: String = "",
    val description: String = "",
) {
}

data class LinkAttachment(
    val link: Link
) : Attachment  {
    override val type = "link"
}
