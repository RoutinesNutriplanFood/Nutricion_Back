package com.example.apisimulacro202245678.repository;

import com.example.apisimulacro202245678.model.Create;
import com.example.apisimulacro202245678.model.Registro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroRepository extends JpaRepository<Registro, Long> {
    //Usando Query Method (Keywords)

}
