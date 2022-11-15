package the.goats.tracedent.api.nuevoApi.Login.Response.Phase2.Clinic

import the.goats.tracedent.api.nuevoApi.Login.Response.Phase1.LoginPhase1ResponseData

data class LoginResponsePhase2ClinicData(
    val id_clinic: String,
    val company_name: String,
    val ruc: String,
    val rating: Float,
    val user: LoginPhase1ResponseData
)