package the.goats.tracedent.interfaces

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import the.goats.tracedent.Api.DefaultResponse
import the.goats.tracedent.Api.Usuario

interface RetrofitService {
    @FormUrlEncoded
    @POST("usuarios")
    fun CreateUser(
        @Field("idUsuario") idUsuario:String,
        @Field("correo") correo:String,
        @Field("contrasena") contrasena:String,
        @Field("tipoUsuario") tipoUsuario:Int
        ):Call<DefaultResponse>
}