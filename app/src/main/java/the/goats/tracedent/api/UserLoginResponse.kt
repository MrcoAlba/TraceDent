package the.goats.tracedent.api

data class UserLoginResponse (
    val token : String,
    val cuenta : List<UserObjectResponse>
)