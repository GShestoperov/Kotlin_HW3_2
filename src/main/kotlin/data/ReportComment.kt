package data

data class ReportComment(
    val ownerId: Long,
    val commentId: Long,
    val reason: Int = 0 // Причина жалобы:
                        //0 — спам;
                        //1 — детская порнография;
                        //2 — экстремизм;
                        //3 — насилие;
                        //4 — пропаганда наркотиков;
                        //5 — материал для взрослых;
                        //6 — оскорбление;
                        //8 — призывы к суициду.
) {
}

class IncorrectReportReasonException(message: String) : RuntimeException(message)