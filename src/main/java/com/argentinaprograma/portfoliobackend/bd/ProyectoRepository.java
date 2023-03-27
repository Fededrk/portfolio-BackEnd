package com.argentinaprograma.portfoliobackend.bd;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProyectoRepository extends CrudRepository<Proyecto, Long> {

    List<Proyecto> findByEmail(String email);
}
