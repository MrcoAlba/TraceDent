package the.goats.tracedent.api.OLDAPI

data class UserLoginResponse (
    val token : String,
    val cuenta : List<UserObjectResponse>
)