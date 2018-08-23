package io.procrastination.foundation.data

import io.procrastination.foundation.domain.errors.UnableToParseDTOException

/**
 * Mapper that converts a DTO object D, into a application model object M.
 * @param D The DTO
 * @param M The Model
*/
interface DTOMapper<D, M> {

    /**
     * Converts the provided DTO (D) into a model (M)
     * @param dto The data transfer object
     * @return The model object
     * @throws UnableToParseDTOException If unable to parse
     */
    @Throws(UnableToParseDTOException::class)
    fun toModel(dto: D): M

    /**
     * Converts a List of the provided DTO (D) into a List of models (M)
     * @param dtos The List of data transfer object
     * @return The model List
     * @throws UnableToParseDTOException If unable to parse
     */
    @Throws(UnableToParseDTOException::class)
    fun toModelList(dtos: List<D>): List<M> = dtos.map { toModel(it) }
}
