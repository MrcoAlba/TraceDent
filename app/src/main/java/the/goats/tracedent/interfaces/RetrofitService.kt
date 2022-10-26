package the.goats.tracedent.interfaces

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import the.goats.tracedent.model.Clinic
import the.goats.tracedent.model.Dentist

interface RetrofitService {
    @GET("dentistas?")
    fun getDentistsList(@Query("name")name:String): Call<MutableList<Dentist>>
    @GET("dentist")
    fun getAllDentistsList(): Call<MutableList<Dentist>>
    @GET("clinic")
    fun getAllClinicsList(): Call<MutableList<Clinic>>
}