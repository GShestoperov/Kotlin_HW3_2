package service

import data.Comment
import data.CommentNotFoundException
import data.CommentOwnerMismatchException
import data.Comments
import data.IncorrectReportReasonException
import data.Post
import data.PostNotFoundException
import data.ReportComment

object WallService {
    private var postArr: Array<Post> = arrayOf<Post>()
    private var nextPostId: Long = 1
    private var comments = emptyArray<Comment>()
    private var nextCommentId: Long = 1
    private var reportComments = emptyArray<ReportComment>()

    fun clearWall() {
        postArr = emptyArray<Post>()
        nextPostId = 1
        comments = emptyArray<Comment>()
        nextCommentId = 1
    }

    fun addPost(post: Post): Post {
        val newPost = post.copy(
            id = nextPostId,
            comments = post.comments.copy(),
            likes = post.likes.copy(),
            attachments = post.attachments.copyOf()
        )

        postArr += newPost

        nextPostId++

        return postArr.last()
    }

    fun updatePost(post: Post): Boolean {
        var foundIndex: Int = -1

        for ((index, item) in postArr.withIndex()) {
            if (item.id == post.id) {
                foundIndex = index
                break
            }
        }

        if (foundIndex == -1) {
            return false
        }

        postArr[foundIndex] = post.copy(
            comments = post.comments.copy(),
            likes = post.likes.copy(),
            attachments = post.attachments.copyOf()
        )

        return true
    }

    fun createComment(postId: Int, comment: Comment): Comment {
        var foundIndex: Int = -1

        for ((index: Int, item: Post) in postArr.withIndex()) {
            if (item.id.toInt() == postId) {
                foundIndex = index
                break
            }
        }

        if (foundIndex == -1) {
            throw PostNotFoundException("No post with id $postId")
        }

        comments += comment.copy(
            id = nextCommentId,
            postId = postId.toLong()
        )

        nextCommentId++

        return comments.last()
    }

    fun reportComment(ownerId: Long, commentId: Long, reason: Int): ReportComment {
        var foundIndex = -1

        for ((index, item) in comments.withIndex()) {
            if (item.id == commentId) {
                foundIndex = index
                break
            }
        }

        if (foundIndex == -1) {
            throw CommentNotFoundException("Comment with id " + commentId + " not found")
        }

        if (comments[foundIndex].fromId != ownerId) {
            throw CommentOwnerMismatchException("Comment ownerId " + comments[foundIndex].fromId + " mismatch the parametr " + ownerId)
        }

        if (!(reason in 0..8)) {
            throw IncorrectReportReasonException("Incorrect report comment reason")
        }

        reportComments += ReportComment(ownerId, commentId, reason)

        return reportComments.last()
    }

    override fun toString(): String {
        var strBuilder: StringBuilder = StringBuilder("Посты на стене: \n")

        for (post in postArr) {
            strBuilder.append("-----------------------------------------------------\n")
            strBuilder.append(post)

            strBuilder.append("Комментации к посту: \n")
            for (comment in comments) {
                if (comment.postId == post.id) {
                    strBuilder.append(comment);
                    strBuilder.append("\n")
                }
            }
        }

        strBuilder.append("\nЖалобы на комментарии: \n")
        for (item in reportComments) {
            strBuilder.append(item)
            strBuilder.append("\n")
        }

        return strBuilder.toString()
    }


}