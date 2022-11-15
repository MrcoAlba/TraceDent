package the.goats.tracedent.api.nuevoApi.Login.Response.Phase2.Dentist

import the.goats.tracedent.api.nuevoApi.MetaDataResponse

data class LoginResponsePhase2Dentist (
        val message:String,
        val data: LoginResponsePhase2DentistData,
        val meta: MetaDataResponse
)