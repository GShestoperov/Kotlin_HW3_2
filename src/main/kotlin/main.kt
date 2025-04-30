import service.NoteService

fun main() {
    NoteService.add("Hello", "Hello Everybody")

    val note = NoteService.getById(1)

    println(note)

    NoteService.add("Hello2", "Hello Everybody 2")
    NoteService.add("Hello3", "Hello Everybody 3")

    val notes = NoteService.get(listOf(1, 3))

    println("NOTES --------------------------------------------")
    println(notes)

    NoteService.createComment(2, "My comment!")
    NoteService.createComment(2, "My comment2!")
    NoteService.createComment(3, "My comment3!")

    val noteComments = NoteService.getComments(2)

    println("COMMENTS2 --------------------------------------------")
    println(noteComments)

    NoteService.delete(1)

    val notes2 = NoteService.get(listOf(2, 3))

    println("NOTES2 --------------------------------------------")
    println(notes2)

    NoteService.deleteComment(1)
    val noteComments3 = NoteService.getComments(2)

    println("COMMENTS3 --------------------------------------------")
    println(noteComments3)

    NoteService.restoreComment(1)
    val noteComments4 = NoteService.getComments(2)

    println("COMMENTS4 --------------------------------------------")
    println(noteComments4)

    NoteService.edit(2, "New 2", "Hello NEW 2")
    val notes3 = NoteService.get()

    println("NOTES3 --------------------------------------------")
    println(notes3)

    NoteService.editComment(1, "New comment!")
    val noteComments5 = NoteService.getComments(2)

    println("COMMENTS5 --------------------------------------------")
    println(noteComments5)

}
