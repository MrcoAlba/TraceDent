package the.goats.tracedent.api.clinic

data class ClinicCreation (
        val mail: String?,
        val pswd: String?,
        val phone_number: Int?,
        val district: String?,
        val direction: String?,
        val latitude: Float?,
        val longitude: Float?,
        val company_name: String?,
        val ruc: String?
)