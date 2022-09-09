package the.goats.tracedent.interfaces

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import the.goats.tracedent.api.DefaultResponse
import the.goats.tracedent.api.Usuario

interface ApiService {
    @POST("usuarios")
    fun InserUsert(@Body usuario: Usuario):Call<DefaultResponse>
}