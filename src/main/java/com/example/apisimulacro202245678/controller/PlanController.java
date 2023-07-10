package com.example.apisimulacro202245678.controller;

import com.example.apisimulacro202245678.exception.ValidationException;
import com.example.apisimulacro202245678.model.Experto;
import com.example.apisimulacro202245678.model.Plan;
import com.example.apisimulacro202245678.repository.PlanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class PlanController {

    private final PlanRepository planRepository;

    public PlanController(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    //Endopint (url): http://localhost:8080/api/v1/planes
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/planes")
    public ResponseEntity<List<Plan>> getAllPlanes() {
        return new ResponseEntity<List<Plan>>(planRepository.findAll(), HttpStatus.OK);
    }


    //Endopint (url): http://localhost:8080/api/v1/employees
    //Method: POST
    @Transactional
    @PostMapping("/planes")
    public ResponseEntity<Plan> createPlan(@RequestBody Plan plan) {
        validatePlan(plan);
        return new ResponseEntity<Plan>(planRepository.save(plan), HttpStatus.CREATED);
    }

    //Endopint (url): http://localhost:8080/api/v1/employees
    //Method: Delete
    @Transactional
    @DeleteMapping("/planes/{id}")
    public ResponseEntity<?> deletePlan(@PathVariable Long id) {
        planRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


    //Endopint (url): http://localhost:8080/api/v1/employees
    //Method: Edit
    @Transactional
    @PutMapping("/edit-planes/{id}")
    public ResponseEntity<Plan> updatePlan(@PathVariable Long id, @RequestBody Plan plan) {
        Plan existingPlan = planRepository.findById(id).orElse(null);
        if (existingPlan == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Actualizar los campos del plan existente con los datos del plan enviado en la solicitud

        if ( plan.getName() != null){
            existingPlan.setName(plan.getName());
        }
        if ( plan.getDescripcion() != null){
            existingPlan.setDescripcion(plan.getDescripcion());
        }
        if ( plan.getDuracion() != null){
            existingPlan.setDuracion(plan.getDuracion());
        }
        if ( plan.getObjeticos() != null){
            existingPlan.setObjeticos(plan.getObjeticos());
        }
        if ( plan.getRestricciones() != null){
            existingPlan.setRestricciones(plan.getRestricciones());
        }
        // Guardar los cambios en la base de datos
        Plan updatedPlan = planRepository.save(existingPlan);

        return new ResponseEntity<>(updatedPlan, HttpStatus.OK);
    }



    //Reglas de negocio
    //Validaciones
    private void validatePlan(Plan plan) {
        if (plan.getName() == null || plan.getName().trim().isEmpty()) {
            throw new ValidationException("El nombre del experto es obligatorio");
        }

        if (plan.getName().length() > 50) {
            throw new ValidationException("El nombre del experto no debe exceder los 50 caracteres");
        }
        if (plan.getDescripcion() == null || plan.getDescripcion().trim().isEmpty()) {
            throw new ValidationException("La descripcion del plan obligatoria");
        }
        if (plan.getDescripcion().length() > 500) {
            throw new ValidationException("El plan no debe exceder los 500 caracteres");
        }

        if (plan.getDuracion().length() > 2) {
            throw new ValidationException("La duracion del plan no debe exceder los 2 caracteres");
        }
        if (plan.getDuracion() == null || plan.getDuracion().trim().isEmpty()) {
            throw new ValidationException("La duracion del plan es obligatoria");
        }

        if (plan.getObjeticos() == null || plan.getObjeticos().trim().isEmpty()) {
            throw new ValidationException("Los objetivos del plan son obligatorios");
        }
        if (plan.getObjeticos().length() > 150) {
            throw new ValidationException("El objetivo del plan no debe excedeer los 150 caracteres");
        }

        if (plan.getRestricciones() == null || plan.getRestricciones().trim().isEmpty()) {
            throw new ValidationException("Las restriciones del plan son obligatorias");
        }

        if (plan.getRestricciones().length() > 150) {
            throw new ValidationException("Las resticciones del plan no deben exceder los 150 caracteres");
        }


    }

    //No se debe permitir el registro de un empleado con el mismo nombre y dni.

}