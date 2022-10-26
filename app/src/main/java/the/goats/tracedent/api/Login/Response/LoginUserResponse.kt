package the.goats.tracedent.api.Login.Response

data class LoginUserResponse(
    val id_user : String,
    val user_type : String,
    val mail : String,
    val subscription : Boolean,
)

/*
{
    "id_user": "5fb66910-5509-11ed-b1ed-193cf109d0d9",
    "user_type": "clinic",
    "mail": "clinic08@mail.com",
    "pswd": "clinic08",
    "phone_number": 900000008,
    "subscription": false,
    "district": "distritoc08",
    "direction": "direccionc08",
    "latitude": -12.08,
    "longitude": -77.08
}
 */