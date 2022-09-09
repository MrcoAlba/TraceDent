package the.goats.tracedent.api

data class Clinic (
    val _id: String,
    val correo : String,
    val razonSocial: String,
    val direccion: String,
    val distrito: String,
    val numero: Int,
    val ruc: Int
        )