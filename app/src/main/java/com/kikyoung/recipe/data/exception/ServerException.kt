package com.kikyoung.recipe.data.exception

import java.io.IOException

data class ServerException(override val message: String?) : IOException(message)