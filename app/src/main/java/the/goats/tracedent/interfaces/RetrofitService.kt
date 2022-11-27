package the.goats.tracedent.interfaces

import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import the.goats.tracedent.api.*
import the.goats.tracedent.api.clinic.*
import the.goats.tracedent.api.dentist.*
import the.goats.tracedent.api.patient.*
import the.goats.tracedent.api.user.*
import the.goats.tracedent.model.*


interface RetrofitService {
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    //  ----- ----- ----- -----  ----- ----- ----- USERS ----- ----- ----- ----- ----- ----- ----- -
    /** ----- ----- ----- ----- ----- ----- Email check existence ----- ----- ----- ----- ----- - */
    @GET("user/email/{mail}")
    fun emailCheckExistance
                (@Path("mail") mail: String?)
    : Call<ApiResponse<Int>>
    /** ----- ----- ----- ----- ----- ----- Login Mail Password ----- ----- ----- ----- ----- --- */
    @POST("user/login")
    fun loginMailPass
                (@Body userLoginPhase1Credentials : UserLoginPhase1)
    : Call<ApiResponse<User>>
    /** ----- ----- ----- ----- ----- ----- Patch user subscription by id ----- ----- ----- ----- */
    @PATCH("user/subs/{id}")
    fun patchUserSubById
                (@Path("id") id: String?)
    : Call<ApiResponse<Int>>
    /** ----- ----- ----- ----- ----- ----- Update user by id ----- ----- ----- ----- ----- ----- */
    @PATCH("user/update/{id}")
    fun updateUserById
                (@Body userUpdateData : UserUpdateData,
                 @Path("id") id: String?)
    : Call<ApiResponse<Int>>
    //  ----- ----- ----- -----  ----- ----- ----- USERS ----- ----- ----- ----- ----- ----- ----- -
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    //  ----- ----- ----- -----  ----- ----- ----- PATIENT ----- ----- ----- ----- ----- ----- -----
    /** ----- ----- ----- ----- ----- ----- Get patient by id ----- ----- ----- ----- ----- ----- */
    @GET("patient/{id}?")
    fun getPatientById
                (@Path("id") id: String?)
    : Call<ApiResponse<Patient>>
    /** ----- ----- ----- ----- ----- ----- Post patient ----- ----- ----- ----- ----- ----- ---- */
    @POST("patient")
    fun postPatient
                (@Body patient: PatientCreation)
    : Call<ApiResponse<PatientCreated>>
    /** ----- ----- ----- ----- ----- ----- Log In by IdUser ----- ----- ----- ----- ----- -----  */
    @POST("patient/login")
    fun patientLoginByIdUser
                (@Body patientLoginIdUser : PatientLoginIdUser)
    : Call<ApiResponse<Patient>>
    //  ----- ----- ----- -----  ----- ----- ----- PATIENT ----- ----- ----- ----- ----- ----- -----
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    //  ----- ----- ----- ----- ----- ----- ----- DENTIST ----- ----- ----- ----- ----- ----- -----
    /** ----- ----- ----- ----- ----- ----- Get all dentist ----- ----- ----- ----- ----- -----   */
    @GET("dentist?")
    fun getAllDentistsList
               (@Query("limit")limit:String,
                @Query("offset")offset:String,
                @Query("name")name:String,
                @Query("latitude")latitude: String,
                @Query("longitude")longitude: String)
    : Call<ApiResponse<Dentist>>
    /** ----- ----- ----- ----- ----- ----- Get dentist by id ----- ----- ----- ----- ----- ----- */
    @GET("dentist/{id}?")
    fun getDentistById
                (@Path("id") id: String?)
    : Call<ApiResponse<Dentist>>
    /** ----- ----- ----- ----- ----- ----- Get dentist by id all specialities ----- ----- -----  */
    @GET("dentist/specialities/{id}?")
    fun getDentistByIdAllSpecialities
                (@Path("id") id: String?,
                 @Query("limit")limit:String,
                 @Query("offset")offset:String,
                 @Query("name")name:String)
    : Call<ApiResponse<DentistSpecialities>>
    /** ----- ----- ----- ----- ----- ----- Search dentist by id ----- ----- ----- ----- ----- -- */
    @GET("dentist/{id}")
    fun searchDentistById
                (@Path("id") id: String?)
    : Call<ApiResponse<Dentist>>
    /** ----- ----- ----- ----- ----- ----- Post dentist ----- ----- ----- ----- ----- ----- ---- */
    @POST("dentist")
    fun postDentist
                (@Body dentist: DentistCreation)
    : Call<ApiResponse<DentistCreated>>
    /** ----- ----- ----- ----- ----- ----- Log In by IdUser ----- ----- ----- ----- ----- -----  */
    @POST("dentist/login")
    fun dentistLoginByIdUser
                (@Body dentistLoginIdUser : DentistLoginIdUser)
    : Call<ApiResponse<Dentist>>
    /** ----- ----- ----- ----- ----- ----- Add speciality to dentist by id ----- ----- ----- --- */
    @POST("dentist/speciality")
    fun addSpecialityToDentistById
                (@Body dentistSpecialityInformation: DentistSpecialityAddition)
    : Call<ApiResponse<DentistSpecialityAdded>>
    //  ----- ----- ----- ----- ----- ----- ----- DENTIST ----- ----- ----- ----- ----- ----- -----
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    //  ----- ----- ----- ----- ----- ----- ----- CLINIC ----- ----- ----- ----- ----- ----- ----- -
    /** ----- ----- ----- ----- ----- ----- Get all clinic ----- ----- ----- ----- ----- ----- -- */
    @GET("clinic?")
    fun getAllClinicsList
                (@Query("offset")offset:String,
                 @Query("limit")limit:String,
                 @Query("name")name:String,
                 @Query("latitude")latitude: String,
                 @Query("longitude")longitude: String)
    : Call<ApiResponse<Clinic>>
    /** ----- ----- ----- ----- ----- ----- Get clinic by id ----- ----- ----- ----- ----- ----- */
    @GET("clinic/{id}?")
    fun getClinicById
                (@Path("id") id: String?)
            : Call<ApiResponse<Clinic>>
    /** ----- ----- ----- ----- ----- ----- Get all recruits by id clinic ----- ----- ----- ----- */
    @GET("clinic/recruits/{id}?")
    fun getAllRecruitsByIdClinic
                (@Path("id")id:String?,
                 @Query("offset")offset:String,
                 @Query("limit")limit:String,
                 @Query("name")name:String)
    : Call<ApiResponse<Clinic>>
    /** ----- ----- ----- ----- ----- ----- Get all dentist by id clinic ----- ----- ----- -----  */
    @GET("clinic/dentists/{id}?")
    fun getAllDentistByIdClinic
                (@Path("id")id:String?,
                 @Query("offset")offset:String,
                 @Query("limit")limit:String,
                 @Query("name")name:String)
    : Call<ApiResponse<Recruitment>>
    /** ----- ----- ----- ----- ----- ----- Post clinic ----- ----- ----- ----- ----- ----- ----- */
    @POST("clinic")
    fun postClinic
                (@Body clinic: ClinicCreation)
    : Call<ApiResponse<ClinicCreated>>
    /** ----- ----- ----- ----- ----- ----- Log In by IdUser ----- ----- ----- ----- ----- -----  */
    @POST("clinic/login")
    fun clinicLoginByIdUser
                (@Body clinicLoginIdUser : ClinicLoginIdUser)
    : Call<ApiResponse<Clinic>>
    /** ----- ----- ----- ----- ----- ----- Clinic recruit dentist ----- ----- ----- ----- -----  */
    @POST("clinic/recruit/{id}")
    fun clinicRecruitDentist
                (@Path("id")id:String?,
                 @Body clinicRecruitDentistIdSend : ClinicRecruitDentistIdSend)
    : Call<ApiResponse<ClinicRecruitDentistIdResponse>>
    //  ----- ----- ----- ----- ----- ----- ----- CLINIC ----- ----- ----- ----- ----- ----- ----- -
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    //  ----- ----- ----- ----- ----- ----- ----- DENTIST SPECIALITIES ----- ----- ----- ----- -----
    /** ----- ----- ----- ----- ----- ----- Get all dentist specialities relations ----- ----- -  */
    @GET("dentistspeciality?")
    fun getAllDentistSpecialitiesRelations
                (@Query("offset")offset:String,
                 @Query("limit")limit:String)
    : Call<ApiResponse<DentistSpecialities>>
    //  ----- ----- ----- ----- ----- ----- ----- DENTIST SPECIALITIES ----- ----- ----- ----- -----
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    //  ----- ----- ----- ----- ----- ----- ----- RECRUITMENTS ----- ----- ----- ----- ----- ----- -
    /** ----- ----- ----- ----- ----- ----- Get all recruitments ----- ----- ----- ----- ----- -  */
    @GET("recruitment?")
    fun getAllRecruitments
                (@Query("offset")offset:String,
                 @Query("limit")limit:String)
    : Call<ApiResponse<DentistSpecialities>>
    //  ----- ----- ----- ----- ----- ----- ----- RECRUITMENTS ----- ----- ----- ----- ----- ----- -
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    //  ----- ----- ----- ----- ----- ----- ----- SCHEDULE ----- ----- ----- ----- ----- ----- -----
    /** ----- ----- ----- ----- ----- ----- Get all schedules ----- ----- ----- ----- ----- ----  */
    @GET("schedule?")
    fun getAllSchedule
                (@Query("offset")offset:String,
                 @Query("limit")limit:String)
    : Call<ApiResponse<Schedule>>
    /** ----- ----- ----- ----- ----- ----- Get all schedules by dentist clinic and time ----- -  */
    @GET("schedule/clinic/dentist/time?")
    fun getAllScheduleByClinicDentistAndTime
                (@Query("offset")       offset:String,
                 @Query("limit")        limit:String,
                 @Query("id_clinic")    id_clinic: String,
                 @Query("id_dentist")   id_dentist:String,
                 @Query("date")         date:String,)
            : Call<ApiResponse<Schedule>>
    /** ----- ----- ----- ----- ----- ----- Get all schedules by dentist and time ----- ----- --  */
    @GET("schedule/dentist/only/{id}?")
    fun getAllScheduleByDentistAndTime
                (@Query("offset")       offset:String,
                 @Query("limit")        limit:String,
                 @Query("id_dentist")   id_dentist:String,
                 @Query("date")         date:String,)
            : Call<ApiResponse<Schedule>>
    /** ----- ----- ----- ----- ----- ----- Get all schedules by dentist id ----- ----- ----- --  */
    @GET("schedule/dentist/{id}?")
    fun getAllScheduleByDentistId
                (@Path("id")id:String?,
                 @Query("offset")offset:String,
                 @Query("limit")limit:String,
                 @Query("status")status:Int,
                 @Query("id_clinic")id_clinic:String)
    : Call<ApiResponse<Schedule>>
    /** ----- ----- ----- ----- ----- ----- Get all schedules by clinic id ----- ----- ----- --  */
    @GET("schedule/clinic/{id}?")
    fun getAllScheduleByClinicId
                (@Path("id")id:String?,
                 @Query("offset")offset:String,
                 @Query("limit")limit:String,
                 @Query("status")status:Int)
            : Call<ApiResponse<Schedule>>
    /** ----- ----- ----- ----- ----- ----- Get all schedules by patient id ----- ----- ----- --  */
    @GET("schedule/patient/{id}?")
    fun getAllScheduleByPatientId
                (@Path("id")id:String?,
                 @Query("offset")offset:String,
                 @Query("limit")limit:String,
                 @Query("status")status:Int,
                 @Query("id_clinic")id_clinic:String)
    : Call<ApiResponse<Schedule>>
    /** ----- ----- ----- ----- ----- ----- S0 Create an schedule for dentist by id and clinic id */
    @POST("schedule/dentist/0")
    fun createAnScheduleForDentistByIdAndClinicId
                (@Body schedule : Schedule)
    : Call<ApiResponse<Schedule>>
    /** ----- ----- ----- ----- ----- ----- S1 Dentist cancels schedule ----- ----- ----- ----- - */
    @POST("schedule/dentist/1")
    fun s1dentistCancelSchedule
                (@Body schedule : Schedule)
    : Call<ApiResponse<Schedule>>
    /** ----- ----- ----- ----- ----- ----- S2 Patient intents schedule ----- ----- ----- ----- - */
    @POST("schedule/patient/2")
    fun s2patientIntentSchedule
                (@Body schedule : Schedule)
    : Call<ApiResponse<Schedule>>
    /** ----- ----- ----- ----- ----- ----- S3 Patient chooses schedule ----- ----- ----- ----- - */
    @POST("schedule/patient/3")
    fun s3patientChoosesSchedule
                (@Body schedule : Schedule)
    : Call<ApiResponse<Schedule>>
    //  ----- ----- ----- ----- ----- ----- ----- SCHEDULE ----- ----- ----- ----- ----- ----- -----
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    // *********************************************************************************************
    //  ----- ----- ----- ----- ----- ----- ----- SPECIALITY ----- ----- ----- ----- ----- ----- ---
    /** ----- ----- ----- ----- ----- ----- Get all clinic ----- ----- ----- ----- ----- ----- -- */
    @GET("speciality?")
    fun getSpeciality
                (@Query("offset")offset:String,
                 @Query("limit")limit:String,
                 @Query("name")name:String,
                 @Query("id_speciality")id_speciality:String)
            : Call<ApiResponse<Speciality>>
    //  ----- ----- ----- ----- ----- ----- ----- SPECIALITY ----- ----- ----- ----- ----- ----- ---
}