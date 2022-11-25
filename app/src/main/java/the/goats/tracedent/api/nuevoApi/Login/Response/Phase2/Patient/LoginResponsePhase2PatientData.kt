package the.goats.tracedent.api.nuevoApi.Login.Response.Phase2.Patient

import the.goats.tracedent.api.nuevoApi.Login.Response.Phase2.PersonResponse

data class LoginResponsePhase2PatientData (
    val id_patient: String,
    val person: PersonResponse
        )