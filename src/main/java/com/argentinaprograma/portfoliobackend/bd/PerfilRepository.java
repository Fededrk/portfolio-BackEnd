package com.argentinaprograma.portfoliobackend.bd;

import jdk.internal.perf.Perf;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PerfilRepository extends CrudRepository<Perfil, Long> {

    List<Perfil> findByEmail(String email);
}
