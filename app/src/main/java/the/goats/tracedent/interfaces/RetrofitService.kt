package the.goats.tracedent.interfaces

import retrofit2.Call
import retrofit2.http.*
import the.goats.tracedent.api.DefaultResponse
import the.goats.tracedent.api.SusResponse
import the.goats.tracedent.api.UserLoginResponse
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
import the.goats.tracedent.model.UserPostLogin
import the.goats.tracedent.model.UserSuscription

interface RetrofitService {
    @GET("dentistas?")
    fun getDentistsList(@Query("name")name:String): Call<MutableList<Dentist>>

    @GET("dentistas")
    fun getAllDentistsList(): Call<MutableList<Dentist>>
    @POST("usuarios/login")
    fun getUserAccountInfo(@Body userPostLogin: UserPostLogin): Call<UserLoginResponse>
    @PATCH("user/{id_user}")
    fun ChangeSuscription(@Path("id_user") id: String?,
                          @Body userSuscription: UserSuscription
    ): Call<SusResponse>

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