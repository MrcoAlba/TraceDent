package the.goats.tracedent.Api

data class Usuario(
    val id: String,
    val correo: String,
    val contraseña: String,
    val tipoUsuario: Int
)
