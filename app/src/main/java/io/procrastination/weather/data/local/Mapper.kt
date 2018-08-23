package io.procrastination.weather.data.local

interface Mapper<M,C> {

    fun toModel(cachedItem : C) : M

    fun fromModel(model: M) : C
}