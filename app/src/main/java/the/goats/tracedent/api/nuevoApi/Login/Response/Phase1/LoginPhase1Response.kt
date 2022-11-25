package the.goats.tracedent.api.nuevoApi.Login.Response.Phase1

import the.goats.tracedent.api.nuevoApi.MetaDataResponse

data class LoginPhase1Response (
    val message: String,
    val data: LoginPhase1ResponseData,
    val meta: MetaDataResponse
        )