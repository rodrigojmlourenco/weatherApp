package io.procrastination.weather.domain.protocols

interface Mapper<M, C> {

    fun toModel(cachedItem: C): M

    fun fromModel(model: M): C
}