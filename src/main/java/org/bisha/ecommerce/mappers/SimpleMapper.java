package org.bisha.ecommerce.mappers;


import java.util.List;

public interface SimpleMapper<E, D> {
    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntities(List<D> dtos);

    List<D> toDtos(List<E> entities);
}
