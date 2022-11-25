package the.goats.tracedent.interfaces

import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import the.goats.tracedent.api.OLDAPI.*
import the.goats.tracedent.api.OLDAPI.Clinic
import the.goats.tracedent.api.OLDAPI.Login.Request.LoginPhase1
import the.goats.tracedent.api.OLDAPI.Login.Request.LoginPhase2
import the.goats.tracedent.api.OLDAPI.Login.Response.Phase1.LoginResponsePhase1
import the.goats.tracedent.api.OLDAPI.Login.Response.Phase2.Clinic.LoginPhase2ResponseClinic
import the.goats.tracedent.api.OLDAPI.Login.Response.Phase2.Dentist.LoginPhase2ResponseDentist
import the.goats.tracedent.api.OLDAPI.Login.Response.Phase2.Patient.LoginPhase2ResponsePatient
import the.goats.tracedent.api.nuevoApi.NewApiResponse
import the.goats.tracedent.model.UserPostLogin
import the.goats.tracedent.model.UserSuscription

interface RetrofitService {
    @GET("dentist/search?")
    fun getDentistsList(@Query("name")name:String): Call<MutableList<Dentist>>

    @POST("usuarios/login")
    fun getUserAccountInfo(@Body userPostLogin: UserPostLogin): Call<UserLoginResponse>
    @PATCH("user/sub/{id_user}")
    fun ChangeSuscription(@Path("id_user") id: String?,
                          @Body userSuscription: UserSuscription
    ): Call<SusResponse>

    //LOGIN
    @POST("user/login")
    fun logUser(@Body credentials : LoginPhase1): Call<LoginResponsePhase1>
    @POST("patient/login")
    fun logPatient(@Body id : LoginPhase2): Call<LoginPhase2ResponsePatient>
    @POST("clinic/login")
    fun logClinic(@Body id : LoginPhase2): Call<LoginPhase2ResponseClinic>
    @POST("dentist/login")
    fun logDentist(@Body id : LoginPhase2): Call<LoginPhase2ResponseDentist>

    //REGISTER
    @POST("usuarios")
    fun InserUsert(@Body usuario: Usuario):Call<DefaultResponse>
    @POST("pacientes")
    fun InserPatient(@Body patient: Patient):Call<DefaultResponse>
    @POST("dentistas")
    fun InserDentist(@Body dentist: Dentist):Call<DefaultResponse>
    @POST("clinicas")
    fun InsertClinic(@Body clinic: Clinic):Call<DefaultResponse>

    //RESERVAS Y MAP
    @GET("clinics/search?")
    fun getClinicList(@Query("company_name")name:String): Call<MutableList<Clinic>>
    @GET("dentist?")
    fun getAllDentistsList(@Query("offset")offset:String,
                           @Query("limit")limit:String,
                           @Query("name")name:String,
                           @Query("latitude")latitude: String,
                           @Query("longitude")longitude: String): Call<NewApiResponse<Dentist>>
    @GET("clinic")
    fun getAllClinicsList(@Query("offset")offset:String,
                          @Query("limit")limit:String,
                          @Query("name")name:String,
                          @Query("latitude")latitude: String,
                          @Query("longitude")longitude: String): Call<NewApiResponse<Clinic>>

    @GET("clinic/recruit/")
    fun getTheDentistsInAClinic(@Query("id")id:String): Call<MutableList<Recruitment>>

    @GET("dentist/speciality/")
    fun getTheEspecialidadInADentists(@Query("id")id_dentist:String): Call<MutableList<String>>

    @GET("dentist/")
    fun getTheDentistById(@Query("id")name:String): Call<MutableList<Dentist>>

    @GET("dentist/search/id?")
    fun getTheScheduleDentistById(@Query("id_dentist")id_dentist:String,@Query("dia")dia:String):
            Call<MutableList<String>>


    @GET("patient/search/id?")
    fun getPatient(@Query("id_cita")id_cita:String):
            Call<NewApiResponse<Patient>>

    @GET("dentist/search/id?")
    fun getTheScheduleOfAClinicDentistById(@Query("id_clinic")id_clinic:String,
                                           @Query("id_dentist")id_dentist:String,
                                           @Query("dia")dia:String):
            Call<MutableList<String>>
}