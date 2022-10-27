package the.goats.tracedent.interfaces

import retrofit2.Call
import retrofit2.http.*
import the.goats.tracedent.api.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import the.goats.tracedent.api.Clinic
import the.goats.tracedent.api.Login.Request.LoginPhase1
import the.goats.tracedent.api.Login.Request.LoginPhase2
import the.goats.tracedent.api.Login.Response.Phase1.LoginResponsePhase1
import the.goats.tracedent.api.Login.Response.Phase2.Clinic.LoginPhase2ResponseClinic
import the.goats.tracedent.api.Login.Response.Phase2.Dentist.LoginPhase2ResponseDentist
import the.goats.tracedent.api.Login.Response.Phase2.Patient.LoginPhase2ResponsePatient
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




    //LOGIN
    @POST("user/login")
    fun logUser(@Body credentials : LoginPhase1): Call<LoginResponsePhase1>

    @POST("patient/login")
    fun logPatient(@Body id : LoginPhase2 ): Call<LoginPhase2ResponsePatient>

    @POST("clinic/login")
    fun logClinic(@Body id : LoginPhase2 ): Call<LoginPhase2ResponseClinic>

    @POST("dentist/login")
    fun logDentist(@Body id : LoginPhase2 ): Call<LoginPhase2ResponseDentist>





    //REGISTER

    @POST("usuarios")
    fun InserUsert(@Body usuario: Usuario):Call<DefaultResponse>

    @POST("pacientes")
    fun InserPatient(@Body patient: Patient):Call<DefaultResponse>

    @POST("dentistas")
    fun InserDentist(@Body dentist: the.goats.tracedent.api.Dentist):Call<DefaultResponse>

    @POST("clinicas")
    fun InsertClinic(@Body clinic: Clinic):Call<DefaultResponse>

}