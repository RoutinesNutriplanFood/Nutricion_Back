package com.example.apisimulacro202245678.controller;

import com.example.apisimulacro202245678.exception.ValidationException;
import com.example.apisimulacro202245678.model.Create;
import com.example.apisimulacro202245678.model.Experto;
import com.example.apisimulacro202245678.repository.CreateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class CreateController {

    private final CreateRepository createRepository;

    public CreateController(CreateRepository createRepository){
        this.createRepository=createRepository;
    }

    //Endopint (url): http://localhost:8080/api/v1/employees
    //Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/creates")
    public ResponseEntity<List<Create>> getAllCreates(){
        return new ResponseEntity<List<Create>>(createRepository.findAll(), HttpStatus.OK);
    }


    //Endopint (url): http://localhost:8080/api/v1/employees
    //Method: POST
    @Transactional
    @PostMapping("/creates")
    public ResponseEntity<Create> createCreate(@RequestBody Create create){
        existsCreateByNameOrDni(create);
        validateCreate(create);
        return new ResponseEntity<Create>(createRepository.save(create), HttpStatus.CREATED);
    }
    //Endopint (url): http://localhost:8080/api/v1/creates/reset-password
    //Method: POST
    @Transactional
    @PostMapping("/creates/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String newPassword = request.get("newPassword");

        if (name == null || newPassword == null) {
            throw new ValidationException("Faltan datos obligatorios en la solicitud");
        }

        Create create = createRepository.findByName(name);
        if (create == null) {
            throw new ValidationException("El usuario no existe");
        }

        create.setPassword(newPassword);
        createRepository.save(create);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @PutMapping("/creates/reset-progreso/{id}")
    public ResponseEntity<?> resetProgreso(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String newPeso = request.get("newPeso");
        String newAltura = request.get("newAltura");

        if (newPeso == null || newAltura == null) {
            throw new ValidationException("Faltan datos obligatorios en la solicitud");
        }

        // Obtener todos los usuarios
        Create create = createRepository.findById(id).orElse(null);

        // Actualizar el peso y la altura de todos los usuarios

            create.setPeso(newPeso);
            create.setAltura(newAltura);


        // Guardar los cambios en la base de datos
        //createRepository.save(create);



        return ResponseEntity.ok().build();
    }

    //Endopint (url): http://localhost:8080/api/v1/employees
    //Method: Delete
    @Transactional
    @DeleteMapping("/creates/{id}")
    public ResponseEntity<?> deleteCreate(@PathVariable Long id) {
        createRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    @Transactional
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        if (email == null || password == null) {
            throw new ValidationException("Faltan datos obligatorios en la solicitud");
        }

        Create create = createRepository.findByEmail(email);
        if (create == null) {
            throw new ValidationException("El usuario no existe");
        }
        if (!create.getPassword().equals(password)) {
            throw new ValidationException("La contraseña es incorrecta");
        }
        return new ResponseEntity<>(create, HttpStatus.OK);
    }


    @Transactional
    @PutMapping("/edit-usuario/{id}")
    public ResponseEntity<Create> updateCreate(@PathVariable Long id, @RequestBody Create create) {
        Create existingCreate = createRepository.findById(id).orElse(null);
        if (existingCreate == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Actualizar los campos del experto existente con los datos del experto enviado en la solicitud

        if ( create.getName() != null){
            existingCreate.setName(create.getName());
        }

        if ( create.getEmail() != null){
            existingCreate.setEmail(create.getEmail());
        }
        if ( create.getPassword() != null){
            existingCreate.setPassword(create.getPassword());
        }
        if ( create.getPeso() != null){
            existingCreate.setPeso(create.getPeso());
        }
        if ( create.getAltura() != null){
            existingCreate.setAltura(create.getAltura());
        }

        // Guardar los cambios en la base de datos
        Create updatedCreate = createRepository.save(existingCreate);

        return new ResponseEntity<>(updatedCreate, HttpStatus.OK);
    }
    //Reglas de negocio
    //Validaciones
    private void validateCreate(Create create){
        if(create.getName()==null || create.getName().trim().isEmpty()){
            throw new ValidationException("El nombre de usuario es obligatorio");
        }

        if(create.getName().length()>30){
            throw new ValidationException("El nombre de usuario no debe exceder los 30 caracteres");
        }

        if(create.getEmail()==null || create.getEmail().trim().isEmpty()){
            throw new ValidationException("El correo es obligatorio");
        }

        if(create.getPassword()==null || create.getPassword().trim().isEmpty()){
            throw new ValidationException("La contraseña  es obligatoria");
        }

        if(create.getPassword().length()<8){
            throw new ValidationException("La contraseña debe tener minimo 8 caracteres");
        }

        if(create.getPeso()==null || create.getPeso().trim().isEmpty()){
            throw new ValidationException("El peso es obligatoria");
        }

        if(create.getPeso().length()>3){
            throw new ValidationException("La peso no debe exceder minimo 3 caracteres");
        }

        if(create.getAltura()==null || create.getAltura().trim().isEmpty()){
            throw new ValidationException("La altura  es obligatoria");
        }

        if(create.getAltura().length()>3){
            throw new ValidationException("La altura debe exceder 3 caracteres");
        }
    }

    //No se debe permitir el registro de un empleado con el mismo nombre y dni.
    private void existsCreateByNameOrDni(Create create){

        if(createRepository.existsByEmail(create.getEmail())){
            throw new ValidationException("Ese correo ya está registrado");
        }

    }



}
