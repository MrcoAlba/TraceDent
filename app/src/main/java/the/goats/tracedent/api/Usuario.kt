package the.goats.tracedent.api

data class Usuario(
    val id_user: String,
    val user_type: String,
    val phone_number : Long,
    val subscription : Boolean,
    val district : String,
    val direction : String,
    val latitude : Double,
    val longitude : Double
)
