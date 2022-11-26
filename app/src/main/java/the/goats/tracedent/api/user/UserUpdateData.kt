package the.goats.tracedent.api.user

data class UserUpdateData (
    val phone_number: Int?,
    val district: String?,
    val direction: String?,
    val latitude: Float?,
    val longitude: Float?
)