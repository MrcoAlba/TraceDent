package the.goats.tracedent.interfaces

import retrofit2.Call
import retrofit2.http.*
import the.goats.tracedent.api.DefaultResponse
import the.goats.tracedent.api.SusResponse
import the.goats.tracedent.api.UserLoginResponse
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
}