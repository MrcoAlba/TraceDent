package the.goats.tracedent.api.nuevoApi.Login.Response.Phase2.Clinic

import the.goats.tracedent.api.nuevoApi.MetaDataResponse

data class LoginResponsePhase2Clinic (
    val message : String,
    val data: LoginResponsePhase2ClinicData,
    val meta: MetaDataResponse
        )