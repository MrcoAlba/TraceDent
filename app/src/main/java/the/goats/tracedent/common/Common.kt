package the.goats.tracedent.common

import the.goats.tracedent.interfaces.RetrofitService
import the.goats.tracedent.retrofit.RetrofitClient

object Common {
    private val BASE_URL="https://tracedent-api.herokuapp.com/api/"
    val retrofitService: RetrofitService
    get()=RetrofitClient.getClient(BASE_URL).create(RetrofitService::class.java)
}