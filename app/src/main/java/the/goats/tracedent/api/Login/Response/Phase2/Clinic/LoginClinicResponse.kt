package the.goats.tracedent.api.Login.Response.Phase2.Clinic

data class LoginClinicResponse (
    val id_clinic : String,
    val company_name : String,
    val rating : Float,
    val ruc : String
        )