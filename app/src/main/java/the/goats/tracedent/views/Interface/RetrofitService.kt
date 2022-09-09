package the.goats.tracedent.views.Interface

import retrofit2.Call
import retrofit2.http.GET
import the.goats.tracedent.views.model.Dentista

interface RetrofitService {
    @GET("dentistas")
    fun getDentistasList(): Call<MutableList<Dentista>>

}