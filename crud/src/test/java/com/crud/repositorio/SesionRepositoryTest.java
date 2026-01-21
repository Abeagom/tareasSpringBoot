package com.crud.repositorio;

import com.crud.modelo.Sesion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SesionRepositoryTest {

    @Autowired
    private SesionRepository repository;

    @Test
    public void testGuardarSesion() {
        Sesion s = new Sesion(LocalDateTime.now(), "Online", "Pendiente", 60, "Prueba Test");
        Sesion guardada = repository.save(s);
        assertThat(guardada.getId()).isNotNull();
        assertThat(repository.findById(guardada.getId())).isPresent();
    }
}