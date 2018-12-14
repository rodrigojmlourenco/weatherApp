package io.procrastination.weather.domain.model

import androidx.annotation.IntDef

const val NORTH = 0
const val NORTH_EAST = 1
const val EAST = 2
const val SOUTH_EAST = 3
const val SOUTH = 4
const val SOUTH_WEST = 5
const val WEST = 6
const val NORTH_WEST = 7

@IntDef(NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST)
@Retention(AnnotationRetention.SOURCE)
annotation class Direction