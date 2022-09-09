package the.goats.tracedent.Api

data class Dentist (
    val _id: String,
    val correo : String,
    val nombres: String,
    val apellidos: String,
    val direccion: String,
    val distrito: String,
    val numero: Number,
    val genero: String,
    val dni: Number
        )