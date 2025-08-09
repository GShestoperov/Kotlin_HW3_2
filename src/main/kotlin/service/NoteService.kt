package service

import data.CommentNotFoundException
import data.Note
import data.NoteComment
import data.NoteCommentIsDeletedException
import data.NoteCommentNotFoundException
import data.NoteNotFoundException
import java.time.LocalDateTime.now

object NoteService {

    private var nextNid = 1L
    private val notes = mutableListOf<Note>()
    private var nextCid = 1L
    private val noteComments = mutableListOf<NoteComment>()


    fun clearNotes() {
        notes.clear()
        nextNid = 1L
        noteComments.clear()
        nextCid = 1L
    }

    fun add(title: String,
            text: String,
            privacy: Int = 0,
            //commentPrivacy: Int = 0,
            //privacyView: String = "",
            //privacyComment: String = ""
            ): Long {

        notes.add(Note(
            nid = nextNid,
            title = title,
            text = text,
            dateTime = now(),
            //comments = 0,
            viewUrl = "",
            privacy = privacy//,
            //canComment = true,
            //commentPrivacy = commentPrivacy,
            //privacyView = privacyView,
            //privacyComment = privacyComment
        ))

        nextNid++

        return notes.last().nid
    }

    fun getById(nid: Long): Note {
        var foundIndex = -1

        for ((index, value) in notes.withIndex()) {
            if (value.nid == nid) {
                foundIndex = index
                break
            }
        }

        if (foundIndex == -1) {
            throw NoteNotFoundException("Note with id = $nid not found")
        }

        return notes[foundIndex].copy()
       }

    fun get(nids: List<Long> = listOf()): MutableList<Note> {
        val resultNotes = mutableListOf<Note>()

        if (nids.isNotEmpty()) {
            for ((curIndex, curNote) in notes.withIndex()) {
                for (findNid in nids) {
                    if (curNote.nid == findNid) {
                        resultNotes.add(curNote.copy())
                        break
                    }
                }
            }
        } else {
            for (curNote in notes) {
                resultNotes.add(curNote.copy())
            }
        }
        return resultNotes
    }

    fun createComment(
            nid: Long,
            message: String
    ): Long {

        var isFound = false

        for (item in notes) {
            if (item.nid == nid) {
                isFound = true
                break
            }
        }

        if (!isFound) {
            throw NoteNotFoundException("Note with id = $nid not found")
        }

        noteComments.add(NoteComment(
            cid = nextCid,
            nid = nid,
            message = message,
            dateTime = now(),
            isDeleted = false
        ))

        nextCid++

        return noteComments.last().cid
    }

    fun getComments(nid: Long): MutableList<NoteComment> {
        val resultNoteComments = mutableListOf<NoteComment>()

        for (curNoteComment in noteComments) {
            if (curNoteComment.nid == nid && !curNoteComment.isDeleted) {
                resultNoteComments.add(curNoteComment.copy())
            }
        }

        return resultNoteComments
    }

    fun delete(nid: Long): Int {
        var foundIndex = -1

        for ((index, value) in notes.withIndex()) {
            if (value.nid == nid) {
                foundIndex = index
                break
            }
        }

        if (foundIndex == -1) {
            throw NoteNotFoundException("Note with id = $nid not found")
        }

        // удаляем комментарии удаляемой заметки
        val it = noteComments.listIterator()
        while (it.hasNext()) {
            val item = it.next()
            if (item.nid == nid) {
                it.remove()
            }
        }

        notes.removeAt(foundIndex)

        return 1
    }

    fun deleteComment(cid: Long): Int {
        var foundIndex = -1

        for ((index, value) in noteComments.withIndex()) {
            if (value.cid == cid) {
                foundIndex = index
                break
            }
        }

        if (foundIndex == -1) {
            throw NoteCommentNotFoundException("Note comment with id = $cid not found")
        }

        noteComments[foundIndex].isDeleted = true

        return 1
    }

    fun restoreComment(cid: Long): Int {
        var foundIndex = -1

        for ((index, value) in noteComments.withIndex()) {
            if (value.cid == cid) {
                foundIndex = index
                break
            }
        }

        if (foundIndex == -1) {
            throw NoteCommentNotFoundException("Note comment with id = $cid not found")
        }

        noteComments[foundIndex].isDeleted = false

        return 1
    }

    fun edit(
            nid: Long,
            title: String,
            text: String,
            privacy: Int = 0//,
            //commentPrivacy: Int = 0,
            //privacyView: String = "",
            //privacyComment: String = ""
        ): Long {

        var foundIndex = -1

        for ((index, value) in notes.withIndex()) {
            if (value.nid == nid) {
                foundIndex = index
                break
            }
        }

        if (foundIndex == -1) {
            throw NoteNotFoundException("Note with id = $nid not found")
        }

        notes[foundIndex] = Note(
            nid = nid,
            title = title,
            text = text,
            dateTime = notes[foundIndex].dateTime,
            //comments = notes[foundIndex].comments,
            viewUrl = notes[foundIndex].viewUrl,
            privacy = privacy//,
            //canComment = notes[foundIndex].canComment,
            //commentPrivacy = commentPrivacy,
            //privacyView = privacyView,
            //privacyComment = privacyComment
        )

        return 1
    }

    fun editComment(cid: Long, message: String): Int {
        var foundIndex = -1

        for ((index, value) in noteComments.withIndex()) {
            if (value.cid == cid) {
                foundIndex = index
                break
            }
        }

        if (foundIndex == -1) {
            throw CommentNotFoundException("Note comment with id = $cid not found")
        }

        if (noteComments[foundIndex].isDeleted) {
            throw NoteCommentIsDeletedException("Note comment with id = $cid is deleted")
        }

        noteComments[foundIndex] = noteComments[foundIndex].copy(message = message)

        return 1
    }

}