package com.satufernandezks.pruebatecnica.infrastructure.mappers;

import java.util.Optional;

public interface Mapper<D, E> {

    Optional<D> map(E entity);
}
