package the.goats.tracedent.views.fragments

import android.telecom.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("/dentistas?")
    fun getCharacterByName(@Query("name") name:String): Response<DentistaResponse>
    @GET("tracedent-api.herokuapp.com/api/dentistas")
    fun getEvererything(): Response<DentistaResponse>
}