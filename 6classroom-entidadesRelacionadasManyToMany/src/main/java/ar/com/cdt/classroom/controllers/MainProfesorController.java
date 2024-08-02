package ar.com.cdt.classroom.controllers;

import ar.com.cdt.classroom.model.Profesor;
import ar.com.cdt.classroom.service.IProfesorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profesores")
public class MainProfesorController {

    @Autowired
    IProfesorService service;

    @GetMapping
    public ResponseEntity<List<Profesor>> listarProfesores() {
        List<Profesor> profesores = service.listarProfesores();
        return ResponseEntity.ok(profesores);
    }

    @PostMapping
    public ResponseEntity<String> insertarProfesor(@RequestBody Profesor profesor) {
        service.insertarProfesor(profesor);
        return new ResponseEntity<>("Profesor insertado de forma existosa", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modificarProfesor(@PathVariable("id") Integer id, @RequestBody Profesor profesor) {
        if (service.modificarProfesor(id, profesor)) {
            return new ResponseEntity<>("Profesor modificado exitosamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Profesor no encontrado -Controler", HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProfesor(@PathVariable("id") Integer id) {
        if (service.eliminarProfesor(id)) {
            return new ResponseEntity<>("Profesor eliminado satisfactoriamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Profesor no encontrado -Controler", HttpStatus.NOT_FOUND);
        }
    }
}
