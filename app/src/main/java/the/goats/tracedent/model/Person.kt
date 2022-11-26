package the.goats.tracedent.model

data class Person (
    val id_person : String?,
    val first_name : String?,
    val last_name : String?,
    val gender : String?,
    val dni: Long?,
    val user: User?
)