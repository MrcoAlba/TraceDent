package the.goats.tracedent.common

import the.goats.tracedent.interfaces.RetrofitService
import the.goats.tracedent.retrofit.RetrofitClient

object Common {
    private const val BASE_URL="https://tracedent.herokuapp.com/"
    val retrofitService: RetrofitService
    get()=RetrofitClient
        .getClient(BASE_URL)
        .create(RetrofitService::class.java)
}