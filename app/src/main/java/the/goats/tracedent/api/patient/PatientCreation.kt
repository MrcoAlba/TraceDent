package the.goats.tracedent.api.patient

data class PatientCreation (
    val mail: String?,
    val pswd: String?,
    val phone_number: Int?,
    val district: String?,
    val direction: String?,
    val latitude: Float?,
    val longitude: Float?,
    val first_name: String?,
    val last_name: String?,
    val gender: String?,
    val dni: Int?
)