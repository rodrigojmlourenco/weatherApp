package io.procrastination.foundation.domain.errors

import java.lang.RuntimeException

class UnableToParseDTOException : Exception()

class ServiceNotBuiltException(private val service: Any) : Exception() {
    override val message: String?
        get() = String.format("The service %s must be built in its constructor", service::class.java.simpleName)
}

class UnableToBuildServiceException(message : String) : RuntimeException(message)