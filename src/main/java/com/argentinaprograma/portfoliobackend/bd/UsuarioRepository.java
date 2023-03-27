package com.argentinaprograma.portfoliobackend.bd;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    // @Query(value = "FROM Usuario u WHERE u.email = :email AND u.password = :pass")
    List<Usuario> findUsuarioByEmailAndPass(String email, String pass);

}
