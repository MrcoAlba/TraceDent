package the.goats.tracedent.views.common

import retrofit2.create
import the.goats.tracedent.views.Interface.RetrofitService
import the.goats.tracedent.views.retrofit.RetrofitClient

object Common {
    private val BASE_URL="https://tracedent-api.herokuapp.com/api/"
    val retrofitService:RetrofitService
    get()=RetrofitClient.getClient(BASE_URL).create(RetrofitService::class.java)
}