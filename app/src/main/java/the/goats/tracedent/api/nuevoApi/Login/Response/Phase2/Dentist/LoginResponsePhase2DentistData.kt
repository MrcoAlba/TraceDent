package the.goats.tracedent.api.nuevoApi.Login.Response.Phase2.Dentist

import the.goats.tracedent.api.nuevoApi.Login.Response.Phase2.PersonResponse

data class LoginResponsePhase2DentistData (
    val id_dentist: String,
    val ruc: String,
    val rating: Float,
    val person: PersonResponse
        )