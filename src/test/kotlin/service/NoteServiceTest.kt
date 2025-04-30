package service

import data.CommentNotFoundException
import data.Note
import data.NoteComment
import data.NoteNotFoundException
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class NoteServiceTest {

    @Before
    fun clearNoteService() {
        NoteService.clearNotes()
    }

    @Test
    fun addNote() {
        val result = NoteService.add("Hello", "Hello Everybody")

        assertEquals(result, 1)
    }

    @Test
    fun getById_OK() {
        val nid = NoteService.add("Hello", "Hello Everybody")
        val foundNote = NoteService.getById(nid)

        assertEquals(Note(nid, "Hello", "Hello Everybody", dateTime = foundNote.dateTime), foundNote)
    }

    @Test(expected = NoteNotFoundException::class)
    fun getById_NotFound() {
        val nid = NoteService.add("Hello", "Hello Everybody")
        val foundNote = NoteService.getById(nid + 100)
    }

    @Test
    fun getNotes() {
        NoteService.add("Hello", "Hello Everybody")
        NoteService.add("Hello2", "Hello Everybody 2")
        NoteService.add("Hello3", "Hello Everybody 3")

        val foundNotes = NoteService.get(listOf(1, 3))

        assertEquals(
            listOf(
                Note(1, "Hello", "Hello Everybody", dateTime = foundNotes[0].dateTime),
                Note(3, "Hello3", "Hello Everybody 3", dateTime = foundNotes[1].dateTime)
            ),
            foundNotes
        )
    }

    @Test
    fun createComment_OK() {
        val nid = NoteService.add("Hello", "Hello Everybody")
        val cid = NoteService.createComment(nid, "My comment!")

        assertEquals(cid, 1)
    }

    @Test(expected = NoteNotFoundException::class)
    fun createComment_NotFound() {
        val nid = NoteService.add("Hello", "Hello Everybody")
        val cid = NoteService.createComment(nid + 100, "My comment!")
    }

    @Test
    fun getComments() {
        NoteService.add("Hello", "Hello Everybody")
        NoteService.add("Hello2", "Hello Everybody 2")
        NoteService.add("Hello3", "Hello Everybody 3")

        NoteService.createComment(2, "My comment!")
        NoteService.createComment(2, "My comment2!")
        NoteService.createComment(3, "My comment3!")

        val noteComments = NoteService.getComments(2)

        assertEquals(
            listOf(
                NoteComment(1, 2, "My comment!", dateTime = noteComments[0].dateTime),
                NoteComment(2, 2, "My comment2!", dateTime = noteComments[1].dateTime)
            ),
            noteComments
        )
    }

    @Test
    fun delete_OK() {
        NoteService.add("Hello", "Hello Everybody")
        NoteService.add("Hello2", "Hello Everybody 2")
        NoteService.add("Hello3", "Hello Everybody 3")

        NoteService.delete(1)

        val foundNotes = NoteService.get()

        assertEquals(foundNotes.size, 2)
        assertEquals(foundNotes[0].nid, 2)
        assertEquals(foundNotes[1].nid, 3)
    }

    @Test(expected = NoteNotFoundException::class)
    fun delete_NotFound() {
        NoteService.add("Hello", "Hello Everybody")
        NoteService.add("Hello2", "Hello Everybody 2")
        NoteService.add("Hello3", "Hello Everybody 3")

        NoteService.delete(4)
    }

    @Test
    fun deleteComment_OK() {
        NoteService.add("Hello", "Hello Everybody")
        NoteService.createComment(1, "My comment!")
        NoteService.createComment(1, "My comment!")

        NoteService.deleteComment(1)

        val resultComments = NoteService.getComments(1)

        assertEquals(resultComments.size, 1)
        assertEquals(resultComments[0].cid, 2)
    }

    @Test(expected = CommentNotFoundException::class)
    fun deleteComment_NotFound() {
        NoteService.add("Hello", "Hello Everybody")
        NoteService.createComment(1, "My comment!")
        NoteService.createComment(1, "My comment!")

        NoteService.deleteComment(100)
    }

    @Test
    fun restoreComment_OK() {
        NoteService.add("Hello", "Hello Everybody")
        NoteService.createComment(1, "My comment!")
        NoteService.createComment(1, "My comment! 2")

        NoteService.deleteComment(1)
        NoteService.restoreComment(1)

        val resultComments = NoteService.getComments(1)

        assertEquals(resultComments.size, 2)
        assertEquals(resultComments[0].cid, 1)
        assertEquals(resultComments[1].cid, 2)
    }

    @Test(expected = CommentNotFoundException::class)
    fun restoreComment_NotFound() {
        NoteService.add("Hello", "Hello Everybody")
        NoteService.createComment(1, "My comment!")
        NoteService.createComment(1, "My comment!")

        NoteService.deleteComment(1)
        NoteService.restoreComment(100)
    }

    @Test
    fun edit_OK() {
        NoteService.add("Hello", "Hello Everybody")
        NoteService.edit(1, "New", "Hello NEW")

        val note = NoteService.getById(1)

        assertEquals(
            Note(
                1, "New", "Hello NEW", dateTime = note.dateTime
            ), note
        )
    }

    @Test(expected = NoteNotFoundException::class)
    fun edit_NotFound() {
        NoteService.add("Hello", "Hello Everybody")
        NoteService.edit(100, "New", "Hello NEW")
    }

    @Test
    fun editComment_OK() {
        NoteService.add("Hello", "Hello Everybody")
        NoteService.createComment(1, "My comment!")
        NoteService.createComment(1, "My comment!")

        NoteService.editComment(2, "NEW comment")

        val resultComments = NoteService.getComments(1)

        assertEquals(resultComments.size, 2)
        assertEquals(resultComments[1].cid, 2)
        assertEquals(resultComments[1].message, "NEW comment")
    }

    @Test(expected = CommentNotFoundException::class)
    fun editComment_NotFound() {
        NoteService.add("Hello", "Hello Everybody")
        NoteService.createComment(1, "My comment!")
        NoteService.createComment(1, "My comment!")

        NoteService.editComment(100, "NEW comment")
    }

}