package the.goats.tracedent.interfaces

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import the.goats.tracedent.api.Clinic
import the.goats.tracedent.api.Dentist
import the.goats.tracedent.api.Patient
import the.goats.tracedent.api.DefaultResponse
import the.goats.tracedent.api.Usuario

interface ApiService {
    @POST("usuarios")
    fun InserUsert(@Body usuario: Usuario):Call<DefaultResponse>

    @POST("pacientes")
    fun InserPatient(@Body patient: Patient):Call<DefaultResponse>

    @POST("dentistas")
    fun InserDentist(@Body dentist: Dentist):Call<DefaultResponse>

    @POST("clinicas")
    fun InsertClinic(@Body clinic: Clinic):Call<DefaultResponse>

}