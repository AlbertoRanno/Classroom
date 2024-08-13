package ar.com.cdt.classroom.controllers;

import ar.com.cdt.classroom.model.Alumno;
import ar.com.cdt.classroom.service.IAlumnoService;
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
public class MainAlumnoController {

    @Autowired
    IAlumnoService service;

    @GetMapping
    public ResponseEntity<List<Alumno>> listarAlumnos() {
        //return ResponseEntity.ok(service.listarAlumnos());
        return ResponseEntity.status(HttpStatus.OK)
                .header("Custom-Header", "CustomValue")
                .body(service.listarAlumnos());
    }

    @PostMapping
    public ResponseEntity<String> insertarAlumno(@RequestBody Alumno alumno) {
        service.insertarAlumno(alumno);
        //return new ResponseEntity<>("Alumno ingresado exitosamente", HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Custom-Header", "CustomValue")
                .body("Alumno ingresado exitosamente -FD");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modificarAlumno(@PathVariable("id") Integer id, @RequestBody Alumno alumno) {
        if (service.modificarAlumno(id, alumno)) {
            // return new ResponseEntity<>("Alumno modificado exitosamente", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Custom-Header", "CustomValue")
                    .body("Alumno modificado exitosamente -FD");
        } else {
            // return new ResponseEntity<>("Alumno no encontrado -Controler", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Custom-Header", "CustomValue")
                    .body("Alumno no encontrado -Controler -FD");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarAlumno(@PathVariable("id") Integer id) {
        if (service.eliminarAlumno(id)) {
            // return new ResponseEntity<>("Alumno eliminado existosamente", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Custom-Header", "CustomValue")
                    .body("Alumno eliminado exitosamente -FD");
        } else {
            // return new ResponseEntity<>("Alumno no encontrado", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Custom-Header", "CustomValue")
                    .body("Alumno no encontrado -FD");
        }
    }

    @GetMapping("/{nombreAlumno}")
    public ResponseEntity<?> buscarAlumnoPorNombre(@PathVariable("nombreAlumno") String nombreAlumno) {
        /* Importante! ResponseEntity<?>: El ? indica que el tipo de retorno puede ser cualquier cosa,
         lo que te permite devolver tanto un Alumno como un String.*/
        Alumno alumnoBuscado = service.findByNombre(nombreAlumno);
        if (alumnoBuscado != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Custom-Header", "CustomValue")
                    .body(alumnoBuscado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Custom-Header", "CustomValue")
                    .body("Alumno con ese nombre no encontrado");
        }
    }

    @GetMapping("/sinProfeAsignado")
    public ResponseEntity<?> findByProfesorIsNull() {
        List<Alumno> alumnosSinProfe = service.findByProfesorIsNull();
        if (alumnosSinProfe != null) {
            return ResponseEntity.status(HttpStatus.OK).header("Custom-Header", "CustomValue").body(alumnosSinProfe);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Custom-Header", "CustomValue").body("Todos los alumnos tienen profesor asignado");
        }
    }
}
