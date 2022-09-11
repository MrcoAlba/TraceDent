package the.goats.tracedent.api

data class Patient(
    val _id: String,
    val correo: String,
    val nombres: String,
    val apellidos: String,
    val direccion: String,
    val numero: Int,
    val genero: String,
    val dni: Int,
)