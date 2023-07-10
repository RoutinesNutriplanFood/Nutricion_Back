package com.example.apisimulacro202245678.controller;

import com.example.apisimulacro202245678.exception.ValidationException;
import com.example.apisimulacro202245678.model.Create;
import com.example.apisimulacro202245678.model.Experto;
import com.example.apisimulacro202245678.model.Receta;
import com.example.apisimulacro202245678.repository.RecetaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class RecetaController {

    private final RecetaRepository recetaRepository;

    public RecetaController(RecetaRepository recetaRepository){
        this.recetaRepository=recetaRepository;
    }

    //Endopint (url): http://localhost:8080/api/v1/employees
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/recetas")
    public ResponseEntity<List<Receta>> getAllRecetas(){
        return new ResponseEntity<List<Receta>>(recetaRepository.findAll(), HttpStatus.OK);
    }


    //Endopint (url): http://localhost:8080/api/v1/employees
    //Method: POST
    @Transactional
    @PostMapping("/recetas")
    public ResponseEntity<Receta> createCreate(@RequestBody Receta receta){
        validateReceta(receta);
        return new ResponseEntity<Receta>(recetaRepository.save(receta), HttpStatus.CREATED);
    }


    //Endopint (url): http://localhost:8080/api/v1/employees
    //Method: Delete
    @Transactional
    @DeleteMapping("/recetas/{id}")
    public ResponseEntity<?> deleteReceta(@PathVariable Long id) {
        recetaRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    //Endopint (url): http://localhost:8080/api/v1/employees
    //Method: Edit
    @Transactional
    @PutMapping("/edit-recetas/{id}")
    public ResponseEntity<Receta> updateReceta(@PathVariable Long id, @RequestBody Receta receta) {
        Receta existingReceta = recetaRepository.findById(id).orElse(null);
        if (existingReceta == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Actualizar los campos de la receta existente con los datos de la receta enviado en la solicitud

        if ( receta.getName() != null){
            existingReceta.setName(receta.getName());
        }

        if ( receta.getIngredientes() != null){
            existingReceta.setIngredientes(receta.getIngredientes());
        }
        if ( receta.getInstrucciones() != null){
            existingReceta.setInstrucciones(receta.getInstrucciones());
        }
        if ( receta.getPropiedades() != null){
            existingReceta.setPropiedades(receta.getPropiedades());
        }


        // Guardar los cambios en la base de datos
        Receta updatedReceta = recetaRepository.save(existingReceta);

        return new ResponseEntity<>(updatedReceta, HttpStatus.OK);
    }

    //Reglas de negocio
    //Validaciones
    private void validateReceta(Receta receta){
        if(receta.getName()==null || receta.getName().trim().isEmpty()){
            throw new ValidationException("El nombre de la receta es obligatorio");
        }

        if(receta.getName().length()>30){
            throw new ValidationException("El nombre de la receta no debe exceder los 30 caracteres");
        }

        if (receta.getIngredientes() == null || receta.getIngredientes().trim().isEmpty()) {
            throw new ValidationException("Los ingredientes son obligatorios");
        }
        if(receta.getIngredientes().length()>150){
            throw new ValidationException("Los ingredientes no deben exceder los 150 caracteres");
        }

        if (receta.getInstrucciones() == null || receta.getInstrucciones().trim().isEmpty()) {
            throw new ValidationException("Las instrucciones son obligatorias");
        }

        if (receta.getInstrucciones().length() > 500) {
            throw new ValidationException("Las instrucciones no deben exceder los 500 caracteres");
        }
        if (receta.getPropiedades() == null || receta.getPropiedades().trim().isEmpty()) {
            throw new ValidationException("Las propiedades son obligatorias");
        }

        if(receta.getPropiedades().length()>150){
            throw new ValidationException("Las propiedades no deben exceder los 150 caracteres");
        }


    }

    //No se debe permitir el registro de un empleado con el mismo nombre y dni.


}
