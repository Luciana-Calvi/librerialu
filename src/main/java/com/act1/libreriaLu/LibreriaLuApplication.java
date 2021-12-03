package com.act1.libreriaLu;

import com.act1.libreriaLu.servicios.servicioCliente;
import static javafx.scene.input.KeyCode.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LibreriaLuApplication {

    @Autowired
    private servicioCliente serviciocliente;

    public static void main(String[] args) {
        SpringApplication.run(LibreriaLuApplication.class, args);

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception{
        auth
                .userDetailsService(serviciocliente)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}
