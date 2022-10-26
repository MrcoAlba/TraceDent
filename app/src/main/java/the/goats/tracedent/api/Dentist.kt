package the.goats.tracedent.api

data class Dentist (
    val id_dentist: String,
    val ruc : String,
    val rating: Float,
    val person: Person
        )