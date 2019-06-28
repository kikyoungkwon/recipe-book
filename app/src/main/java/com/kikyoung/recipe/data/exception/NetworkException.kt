package com.kikyoung.recipe.data.exception

import java.io.IOException

/**
 * E.g. No Internet.
 */
data class NetworkException(override val message: String?) : IOException(message)