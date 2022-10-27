package the.goats.tracedent.interfaces

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import the.goats.tracedent.model.Clinic
import the.goats.tracedent.model.Dentist

interface RetrofitService {
    @GET("dentist/search?")
    fun getDentistsList(@Query("name")name:String): Call<MutableList<Dentist>>
    @GET("clinics/search?")
    fun getClinicList(@Query("company_name")name:String): Call<MutableList<Clinic>>
    @GET("dentist")
    fun getAllDentistsList(): Call<MutableList<Dentist>>
    @GET("clinic")
    fun getAllClinicsList(): Call<MutableList<Clinic>>

    @GET("clinic/search/dentist?")
    fun getTheDentistsInAClinic(@Query("id_clinic")id_clinic:String): Call<MutableList<Dentist>>
    @GET("dentist/search/especialidad?")
    fun getTheEspecialidadInADentists(@Query("id_dentist")id_dentist:String): Call<MutableList<String>>
    @GET("dentist/search/id?")
    fun getTheDentistById(@Query("id_dentist")name:String): Call<MutableList<Dentist>>
    @GET("dentist/search/id?")
    fun getTheScheduleDentistById(@Query("id_dentist")id_dentist:String,@Query("dia")dia:String): Call<MutableList<String>>
    @GET("dentist/search/id?")
    fun getTheScheduleOfAClinicDentistById(@Query("id_clinic")id_clinic:String,@Query("id_dentist")id_dentist:String,@Query("dia")dia:String):
            Call<MutableList<String>>
}