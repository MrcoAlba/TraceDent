package the.goats.tracedent.api.nuevoApi.Login.Response.Phase2

import the.goats.tracedent.api.nuevoApi.Login.Response.Phase1.LoginPhase1ResponseData

data class PersonResponse (
    val id_person: String,
    val first_name: String,
    val last_name: String,
    val gender: String,
    val dni : Long,
    val user: LoginPhase1ResponseData
        )