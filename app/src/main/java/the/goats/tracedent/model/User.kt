package the.goats.tracedent.model

data class User(
    val id_user: String?,
    val user_type: String?,
    val phone_number : Long?,
    val subscription : Boolean?,
    val district : String?,
    val direction : String?,
    val latitude : Long?,
    val longitude : Long?
)