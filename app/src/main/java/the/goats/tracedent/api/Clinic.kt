package the.goats.tracedent.api

data class Clinic (
    val id_clinic: String,
    val company_name : String,
    val ruc: String,
    val rating: Float,
    val user : Usuario
        )