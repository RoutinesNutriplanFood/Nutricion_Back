package com.example.apisimulacro202245678.controller;

import com.example.apisimulacro202245678.exception.ValidationException;
import com.example.apisimulacro202245678.model.Registro;
import com.example.apisimulacro202245678.repository.RegistroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class RegistroController {

    private final RegistroRepository registroRepository;

    public RegistroController(RegistroRepository registroRepository){this.registroRepository=registroRepository;
    }

    //Endopint (url): http://localhost:8080/api/v1/employees
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/registros")
    public ResponseEntity<List<Registro>> getAllRegistros(){
        return new ResponseEntity<List<Registro>>(registroRepository.findAll(), HttpStatus.OK);
    }


    //Endopint (url): http://localhost:8080/api/v1/employees
    //Method: POST
    @Transactional
    @PostMapping("/registros")
    public ResponseEntity<Registro> createRegistro(@RequestBody Registro registro){
        validateCreate(registro);
        return new ResponseEntity<Registro>(registroRepository.save(registro), HttpStatus.CREATED);
    }

    //Reglas de negocio
    //Validaciones
    private void validateCreate(Registro registro){
        if(registro.getName()==null || registro.getName().trim().isEmpty()){
            throw new ValidationException("El nombre del alimento es obligatorio");
        }

        if(registro.getName().length()>30){
            throw new ValidationException("El nombre del empleado no debe exceder los 30 caracteres");
        }

        if(registro.getCantidad()==null || registro.getCantidad().trim().isEmpty()){
            throw new ValidationException("Debe ingresar una cantidad obligatoria");
        }



    }

    //No se debe permitir el registro de un empleado con el mismo nombre y dni.


}
