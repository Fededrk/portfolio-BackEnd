package com.argentinaprograma.portfoliobackend;

import com.argentinaprograma.portfoliobackend.bd.*;
import com.argentinaprograma.portfoliobackend.dominio.LoginRequest;
import com.argentinaprograma.portfoliobackend.exceptions.UnauthorizedException;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GeneralController {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired private SkillRepository skillRepository;

    @Autowired private UsuarioRepository usuarioRepository;

    @Autowired private ProyectoRepository proyectoRepository;

    @GetMapping( "/skills")
    @CrossOrigin(origins = "*")
    public ResponseEntity findAllSkills() {
        List<Skill> list = new ArrayList<>();

        skillRepository
                .findAll()
                .iterator()
                .forEachRemaining(list::add)
        ;

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/perfil")
    @CrossOrigin(origins = "*")
    public ResponseEntity miPerfil() {
        return perfilRepository
                .findById(1L)
                .map(perfil -> ResponseEntity.ok(perfil))
                .orElse(ResponseEntity.internalServerError().build());
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "*")
    public ResponseEntity login(@RequestBody @Validated LoginRequest loginRequest) {

        return usuarioRepository.findUsuarioByEmailAndPass(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        ).stream().findFirst()
                .map(u -> {
                    Perfil perfil = perfilRepository.findByEmail(u.getEmail())
                            .stream().findFirst()
                            .orElseThrow(() -> new UnauthorizedException("Not found perfil"));

                    return ResponseEntity.ok(perfil);
                })
                .orElseThrow(() -> new UnauthorizedException("Unauthorized"));
    }

    @GetMapping("/proyectos/{email}")
    private ResponseEntity proyectos(
            @PathVariable @NotNull String email
    ) {
        List<Proyecto> proyectos = proyectoRepository.findByEmail(email);

        if (proyectos.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(proyectos);
    }
}
