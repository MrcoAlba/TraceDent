package the.goats.tracedent.interfaces

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import the.goats.tracedent.api.Clinic
import the.goats.tracedent.api.DefaultResponse
import the.goats.tracedent.api.Login.Request.LoginRequest
import the.goats.tracedent.api.Login.Response.LoginUserResponse
import the.goats.tracedent.api.Patient
import the.goats.tracedent.api.Usuario
import the.goats.tracedent.model.Dentist

interface RetrofitService {
    @GET("dentistas?")
    fun getDentistsList(@Query("name")name:String): Call<MutableList<Dentist>>

    @GET("dentistas")
    fun getAllDentistsList(): Call<MutableList<Dentist>>

    @POST("user/login")
    fun logUser(@Body credentials : LoginRequest): Call<LoginUserResponse>

    @POST("usuarios")
    fun InserUsert(@Body usuario: Usuario):Call<DefaultResponse>

    @POST("pacientes")
    fun InserPatient(@Body patient: Patient):Call<DefaultResponse>

    @POST("dentistas")
    fun InserDentist(@Body dentist: the.goats.tracedent.api.Dentist):Call<DefaultResponse>

    @POST("clinicas")
    fun InsertClinic(@Body clinic: Clinic):Call<DefaultResponse>

}