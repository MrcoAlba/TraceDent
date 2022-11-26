package the.goats.tracedent.model

data class Schedule (
    val id_schedule : String?,
    val date : String?,
    val time : Int?,
    val sttus : Int?,
    val id_patient : String?,
    val id_recruitment : String?,
    val id_dentist : String?,
    val id_speciality : String?,
    val id_comment : String?,
)