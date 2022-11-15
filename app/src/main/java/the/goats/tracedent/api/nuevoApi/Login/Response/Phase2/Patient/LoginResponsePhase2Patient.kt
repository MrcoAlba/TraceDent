package the.goats.tracedent.api.nuevoApi.Login.Response.Phase2.Patient

import the.goats.tracedent.api.nuevoApi.MetaDataResponse

data class LoginResponsePhase2Patient (
    val message: String,
    val data: LoginResponsePhase2PatientData,
    val meta: MetaDataResponse
        )