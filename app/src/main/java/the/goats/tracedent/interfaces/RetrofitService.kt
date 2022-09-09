package the.goats.tracedent.interfaces

import retrofit2.Call
import retrofit2.http.GET
import the.goats.tracedent.model.Dentist

interface RetrofitService {
    @GET("dentistas")
    fun getDentistsList(): Call<MutableList<Dentist>>

}