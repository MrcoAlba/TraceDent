package the.goats.tracedent.interfaces

import retrofit2.Call
import retrofit2.http.*
import the.goats.tracedent.api.OLDAPI.Clinic
import the.goats.tracedent.api.OLDAPI.Dentist
import the.goats.tracedent.api.OLDAPI.Patient
import the.goats.tracedent.api.OLDAPI.DefaultResponse
import the.goats.tracedent.api.OLDAPI.Usuario

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