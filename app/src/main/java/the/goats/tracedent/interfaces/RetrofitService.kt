package the.goats.tracedent.interfaces

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import the.goats.tracedent.api.UserLoginResponse
import the.goats.tracedent.model.Dentist
import the.goats.tracedent.model.UserPostLogin

interface RetrofitService {
    @GET("dentistas?")
    fun getDentistsList(@Query("name")name:String): Call<MutableList<Dentist>>
    @GET("dentistas")
    fun getAllDentistsList(): Call<MutableList<Dentist>>
    @POST("usuarios/login")
    fun getUserAccountInfo(@Body userPostLogin: UserPostLogin): Call<UserLoginResponse>
}