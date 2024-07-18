package ar.com.cdt.classroom.controllers;

import ar.com.cdt.classroom.model.Alumno;
import ar.com.cdt.classroom.repository.IAlumnoRepository;
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
@RequestMapping("/api/alumnos")
/* Define el prefijo común para todas las rutas. Se podrían aclarar en cada caso rutas individuales, pero si son los verbos tradicionales
y el codigo no es muy largo, es buena practica que apunten todos a la misma url, por distintos verbos. Si se hiciera muy grande,
se podria separar en varios controladores de ser necesario. */
public class MainAlumnoController {

    @Autowired
    IAlumnoRepository repo;

    //@GetMapping("/api/get") - evito el /api/ inicial en todas, con el RequestMapping
    //@GetMapping("/get") - evito el /get ingresando por todas las request, a la misma ruta /api, pero por distintos verbos
    @GetMapping
    public ResponseEntity<List<Alumno>> listarAlumnos() { //Es el unico caso que no envio un String, porque envio el listado
        List<Alumno> alumnos = repo.findAll();
        // Devuelve una respuesta 200 OK / HttpStatus.OK (200): La solicitud ha tenido éxito.
        return ResponseEntity.ok(alumnos); 
    }

    //@PostMapping("/post")
    @PostMapping
    public ResponseEntity<String> insertarAlumno(@RequestBody Alumno alumno) {
        // Devuelve una respuesta 201 Created / HttpStatus.CREATED (201): La solicitud ha tenido éxito y se ha creado un nuevo recurso.
        repo.save(alumno);
        return new ResponseEntity<>("Alumno ingresado exitosamente", HttpStatus.CREATED);
    }

    //@PutMapping("/put")
    @PutMapping("/{id}")
    public ResponseEntity<String> modificarAlumno(@PathVariable("id") Integer id, @RequestBody Alumno alumno) {
        //Verifico si el alumno existe
        if (repo.existsById(id)) {
            alumno.setIdAlumno(id); /*(*)*/
            //signa el ID proporcionado en la URL al objeto Alumno que se recibe en el cuerpo de la solicitud
            repo.save(alumno); //Se usa el mismo metodo save, porque si lo encuentra, lo sobreescribe por completo (PUT).
            //Y si no tuviera el condicional, y no encuentra el ID, lo grabaria con ID 0 u otro
            return new ResponseEntity<>("Alumno modificado exitosamente", HttpStatus.OK);
        } else {
            // Devuelve una respuesta 204 No Content / HttpStatus.NO_CONTENT (204): La solicitud ha tenido éxito pero no hay contenido que devolver.
            return new ResponseEntity<>("Alumno no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarAlumno(@PathVariable("id") Integer id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return new ResponseEntity<>("Alumno eliminado existosamente", HttpStatus.OK);
        } else {
            // Devuelve una respuesta 404 Not Found / HttpStatus.NOT_FOUND (404): El recurso solicitado no se ha encontrado.
            return new ResponseEntity<>("Alumno no encontrado", HttpStatus.NOT_FOUND);
        }
    }
}

/*(*)
alumno.setIdAlumno(id); asigna el ID proporcionado en la URL (@PathVariable("id") Integer id) al objeto Alumno que se recibe
en el cuerpo de la solicitud (@RequestBody Alumno alumno).
Esto garantiza que el objeto alumno que se está guardando tiene el ID correcto, correspondiente al registro existente 
que se desea actualizar.

Persistencia en la base de datos:
Al llamar a repo.save(alumno);, Spring Data JPA verificará si el objeto tiene un ID asignado.
Si el ID ya existe en la base de datos, Spring Data JPA actualizará el registro existente.
Si el ID no existe (por ejemplo, si es un nuevo objeto sin ID), Spring Data JPA creará un nuevo registro.*/