package the.goats.tracedent

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitService {

    @POST("usuarios")
    fun RegisterUser(@Body parameter: Usuario)

}