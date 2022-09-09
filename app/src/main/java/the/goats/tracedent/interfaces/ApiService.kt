package the.goats.tracedent.interfaces

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url
import the.goats.tracedent.Api.DefaultResponse
import the.goats.tracedent.Api.Dentist
import the.goats.tracedent.Api.Patient
import the.goats.tracedent.Api.Usuario

interface ApiService {
    @POST("usuarios")
    fun InserUsert(@Body usuario: Usuario):Call<DefaultResponse>

    @POST("pacientes")
    fun InserPatient(@Body patient: Patient):Call<DefaultResponse>

    @POST("dentistas")
    fun InserDentist(@Body dentist: Dentist):Call<DefaultResponse>
}