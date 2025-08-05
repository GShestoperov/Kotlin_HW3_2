package data

data class Post(
    var id: Long,
    val ownerId: Long,
    val fromId: Long,
    val date: Long,
    val replyOwnerId: Long? = null,
    val replyPostId: Long? = null,
    val comments: Comments = Comments(),
    val likes: Likes = Likes(),
    val isFavorite: Boolean = false,
    val postType: String = "post", // Тип записи, может принимать следующие значения: post, copy, reply, postpone, suggest
    val text: String = "",
    var attachments: Array<Attachment> = emptyArray()
) {

    override fun toString(): String {
        val strBuilder = StringBuilder("Post(id=$id, ownerId=$ownerId, fromId=$fromId, date=$date, replyOwnerId=$replyOwnerId, replyPostId=$replyPostId, comments=$comments, likes=$likes, isFavorite=$isFavorite, postType='$postType', text='$text'")

        strBuilder.append(", attachment=[")
        for (attach in attachments) {
            strBuilder.append("'$attach',")
        }
        strBuilder.append("]\n")

        return strBuilder.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (id != other.id) return false
        if (ownerId != other.ownerId) return false
        if (fromId != other.fromId) return false
        if (date != other.date) return false
        if (replyOwnerId != other.replyOwnerId) return false
        if (replyPostId != other.replyPostId) return false
        if (isFavorite != other.isFavorite) return false
        if (comments != other.comments) return false
        if (likes != other.likes) return false
        if (postType != other.postType) return false
        if (text != other.text) return false
        if (!attachments.contentEquals(other.attachments)) return false

        return true
    }

}

class PostNotFoundException(message: String) : RuntimeException(message)