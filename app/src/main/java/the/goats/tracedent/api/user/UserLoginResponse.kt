package the.goats.tracedent.api.user

data class UserLoginResponse (
    val token : String,
    val cuenta : List<UserObjectResponse>
)