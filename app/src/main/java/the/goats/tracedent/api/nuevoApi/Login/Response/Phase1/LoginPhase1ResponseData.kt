package the.goats.tracedent.api.nuevoApi.Login.Response.Phase1

data class LoginPhase1ResponseData (
    val id_user : String,
    val user_type: String,
    val phone_number: Long,
    val subscription: Boolean,
    val district: String,
    val direction: String,
    var latitude: Float,
    var longitude: Float
        )