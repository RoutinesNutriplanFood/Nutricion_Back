package com.example.apisimulacro202245678.controller;

import com.example.apisimulacro202245678.exception.ValidationException;
import com.example.apisimulacro202245678.model.Experto;
import com.example.apisimulacro202245678.repository.ExpertoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class ExpertoController {

    private final ExpertoRepository expertoRepository;

    public ExpertoController(ExpertoRepository expertoRepository) {
        this.expertoRepository = expertoRepository;
    }

    //Endopint (url): http://localhost:8080/api/v1/employees
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/expertos")
    public ResponseEntity<List<Experto>> getAllExpertos() {
        return new ResponseEntity<List<Experto>>(expertoRepository.findAll(), HttpStatus.OK);
    }


    //Endopint (url): http://localhost:8080/api/v1/employees
    //Method: POST
    @Transactional
    @PostMapping("/expertos")
    public ResponseEntity<Experto> createExperto(@RequestBody Experto experto) {
        validateExperto(experto);
        return new ResponseEntity<Experto>(expertoRepository.save(experto), HttpStatus.CREATED);
    }

    //Endopint (url): http://localhost:8080/api/v1/employees
    //Method: Delete
    @Transactional
    @DeleteMapping("/expertos/{id}")
    public ResponseEntity<?> deleteExperto(@PathVariable Long id) {
        expertoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


    //Endopint (url): http://localhost:8080/api/v1/employees
    //Method: Edit
    @Transactional
    @PutMapping("/edit-expertos/{id}")
    public ResponseEntity<Experto> updateExperto(@PathVariable Long id, @RequestBody Experto experto) {
        Experto existingExperto = expertoRepository.findById(id).orElse(null);
        if (existingExperto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Actualizar los campos del experto existente con los datos del experto enviado en la solicitud

        if ( experto.getName() != null){
            existingExperto.setName(experto.getName());
        }

        if ( experto.getEdad() != null){
            existingExperto.setEdad(experto.getEdad());
        }
        if ( experto.getEspecialidad() != null){
            existingExperto.setEspecialidad(experto.getEspecialidad());
        }
        if ( experto.getPresentacion() != null){
            existingExperto.setPresentacion(experto.getPresentacion());
        }
        if ( experto.getPhone() != null){
            existingExperto.setPhone(experto.getPhone());
        }

        // Guardar los cambios en la base de datos
        Experto updatedExperto = expertoRepository.save(existingExperto);

        return new ResponseEntity<>(updatedExperto, HttpStatus.OK);
    }

    //Reglas de negocio
    //Validaciones
    private void validateExperto(Experto experto) {
        if (experto.getName() == null || experto.getName().trim().isEmpty()) {
            throw new ValidationException("El nombre del experto es obligatorio");
        }

        if (experto.getName().length() > 200) {
            throw new ValidationException("El nombre del experto no debe exceder los 50 caracteres");
        }
        if (experto.getEdad() == null || experto.getEdad().trim().isEmpty()) {
            throw new ValidationException("La edad del experto es obligatoria");
        }

        if (experto.getPresentacion().length() > 500) {
            throw new ValidationException("La presentación dell  experto no debe exceder los 150 caracteres");
        }
        if (experto.getPresentacion() == null || experto.getPresentacion().trim().isEmpty()) {
            throw new ValidationException("La presentacion del experto es obligatoria");
        }

        if (experto.getEspecialidad() == null || experto.getEspecialidad().trim().isEmpty()) {
            throw new ValidationException("La especialidad del experto es obligatoria");
        }
        if (experto.getPhone() == null || experto.getPhone().trim().isEmpty()) {
            throw new ValidationException("El telefono del experto es obligatoria");
        }

        if (experto.getPhone().length() < 9) {
            throw new ValidationException("El telefono del experto debe tener 9 caracteres");
        }


    }

    //No se debe permitir el registro de un empleado con el mismo nombre y dni.

}