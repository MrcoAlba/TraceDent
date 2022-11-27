package the.goats.tracedent.api

data class ApiResponse<T> (
    val message: String,
    val data: List<T>,
    val meta: MetaDataResponse
)