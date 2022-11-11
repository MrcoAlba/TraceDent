package the.goats.tracedent.api.nuevoApi

data class NewApiResponse<T> (

    val message: String,
    val data: List<T>,
    val meta:MetaDataResponse

    )