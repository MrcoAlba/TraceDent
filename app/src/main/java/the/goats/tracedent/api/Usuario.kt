package the.goats.tracedent.api

data class Usuario(
    val idUsuario: String,
    val correo: String,
    val contrasena: String,
    val tipoUsuario: Int
)
