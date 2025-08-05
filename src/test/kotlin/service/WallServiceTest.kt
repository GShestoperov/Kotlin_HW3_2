package service

import data.*
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class WallServiceTest {

    @Before
    fun clearBeforeTest() {
        WallService.clearWall()
    }

    @Test
    fun addPost() {
        val result = WallService.addPost(
            Post(
                id = 0,
                ownerId = 1,
                fromId = 1,
                date = 100,
                attachments = arrayOf(
                    PhotoAttachment(
                        Photo(
                            id = 1,
                            ownerId = 1,
                            date = 100,
                            url = "link.ru/photo.jpg"
                        )
                    ),
                    VideoAttachment(
                        Video(
                            id = 2,
                            ownerId = 1,
                            date = 200,
                            url = "link.ru/video.mp4"
                        )
                    )
                )
            )
        )

        assertEquals(
            result, Post(
                id = 1,
                ownerId = 1,
                fromId = 1,
                date = 100,
                attachments = arrayOf(
                    PhotoAttachment(
                        Photo(
                            id = 1,
                            ownerId = 1,
                            date = 100,
                            url = "link.ru/photo.jpg"
                        )
                    ),
                    VideoAttachment(
                        Video(
                            id = 2,
                            ownerId = 1,
                            date = 200,
                            url = "link.ru/video.mp4"
                        )
                    )
                )
            )
        )
    }

    @Test
    fun updatePost_test1() {

        WallService.addPost(
            Post(
                id = 0,
                ownerId = 1,
                fromId = 1,
                date = 100
            )
        )
        WallService.addPost(
            Post(
                id = 0,
                ownerId = 2,
                fromId = 2,
                date = 150
            )
        )

        val result = WallService.updatePost(
            Post(
                id = 1,
                ownerId = 10,
                fromId = 10,
                date = 250
            )
        )

        assertTrue(result)
    }

    @Test
    fun updatePost_test2() {

        WallService.addPost(
            Post(
                id = 0,
                ownerId = 1,
                fromId = 1,
                date = 100
            )
        )
        WallService.addPost(
            Post(
                id = 0,
                ownerId = 2,
                fromId = 2,
                date = 150
            )
        )

        val result = WallService.updatePost(
            Post(
                id = 3,
                ownerId = 10,
                fromId = 10,
                date = 250
            )
        )

        assertFalse(result)
    }

    @Test
    fun createComment_OK() {

        WallService.addPost(
            Post(
                id = 0,
                ownerId = 2,
                fromId = 2,
                date = 150
            )
        )

        val result = WallService.createComment(
            1, Comment(
                1, 1,
                fromId = 1,
                date = 260,
                text = "Это мой первый комментарий"
            )
        )

        assertEquals(
            result,
            Comment(
                1, 1,
                fromId = 1,
                date = 260,
                text = "Это мой первый комментарий"
            )
        )

    }

    @Test(expected = PostNotFoundException::class)
    fun createComment_Exception() {
        WallService.addPost(
            Post(
                id = 0,
                ownerId = 2,
                fromId = 2,
                date = 150
            )
        )

        val result = WallService.createComment(
            2, Comment(
                1, 2,
                fromId = 1,
                date = 260,
                text = "Это мой первый комментарий"
            )
        )

    }


    @Test
    fun reportComment_OK() {
        WallService.addPost(
            Post(
                id = 0,
                ownerId = 2,
                fromId = 2,
                date = 150
            )
        )

        val comment = WallService.createComment(
            1, Comment(
                1, 1,
                fromId = 2,
                date = 260,
                text = "Это мой первый комментарий"
            )
        )

        val result = WallService.reportComment(2, 1, 1)

        assertEquals(ReportComment(2, 1, 1), result)
    }

    @Test(expected = CommentNotFoundException::class)
    fun reportComment_CommentNotFoundException() {
        WallService.addPost(
            Post(
                id = 0,
                ownerId = 2,
                fromId = 2,
                date = 150
            )
        )

        val comment = WallService.createComment(
            1, Comment(
                1, 1,
                fromId = 2,
                date = 260,
                text = "Это мой первый комментарий"
            )
        )

        val result = WallService.reportComment(2, 2, 1)
    }

    @Test(expected = CommentOwnerMismatchException::class)
    fun reportComment_CommentOwnerMismatchException() {
        WallService.addPost(
            Post(
                id = 0,
                ownerId = 2,
                fromId = 2,
                date = 150
            )
        )

        val comment = WallService.createComment(
            1, Comment(
                1, 1,
                fromId = 2,
                date = 260,
                text = "Это мой первый комментарий"
            )
        )

        val result = WallService.reportComment(1, 1, 1)
    }

    @Test(expected = IncorrectReportReasonException::class)
    fun reportComment_IncorrectReportReasonException() {
        WallService.addPost(
            Post(
                id = 0,
                ownerId = 2,
                fromId = 2,
                date = 150
            )
        )

        val comment = WallService.createComment(
            1, Comment(
                1, 1,
                fromId = 2,
                date = 260,
                text = "Это мой первый комментарий"
            )
        )

        val result = WallService.reportComment(2, 1, 9)
    }

}


