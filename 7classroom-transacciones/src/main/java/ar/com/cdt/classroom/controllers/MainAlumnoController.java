package ar.com.cdt.classroom.controllers;

import ar.com.cdt.classroom.exceptions.ResourceNotFoundException;
import ar.com.cdt.classroom.model.Alumno;
import ar.com.cdt.classroom.model.Curso;
import ar.com.cdt.classroom.service.IAlumnoService;
import ar.com.cdt.classroom.service.ICursoService;
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
    //@Qualifier("nombreImpl")
    IAlumnoService service;

    @Autowired
    ICursoService serviceC;

    @GetMapping
    public ResponseEntity<List<Alumno>> listarAlumnos() {
        return ResponseEntity.status(HttpStatus.OK)
                .header("Custom-Header", "CustomValue")
                .body(service.listarAlumnos());
    }

    @PostMapping
    public ResponseEntity<String> insertarAlumno(@RequestBody Alumno alumno) {
        service.insertarAlumno(alumno);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Custom-Header", "CustomValue")
                .body("Alumno ingresado exitosamente -FD");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modificarAlumno(@PathVariable("id") Integer id, @RequestBody Alumno alumno) {
        if (service.modificarAlumno(id, alumno)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Custom-Header", "CustomValue")
                    .body("Alumno modificado exitosamente -FD");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Custom-Header", "CustomValue")
                    .body("Alumno no encontrado -Controler -FD");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarAlumno(@PathVariable("id") Integer id) {
        if (service.eliminarAlumno(id)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Custom-Header", "CustomValue")
                    .body("Alumno eliminado exitosamente -FD");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Custom-Header", "CustomValue")
                    .body("Alumno no encontrado -FD");
        }
    }

    @GetMapping("/{nombreAlumno}")
    public ResponseEntity<?> findByNombre(@PathVariable("nombreAlumno") String nombreAlumno) {
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

    @GetMapping("/query/{nombreAlumno}")
    public ResponseEntity<?> buscarAlumnoPorNombre(@PathVariable("nombreAlumno") String nombreAlumno) {
        Alumno alumnoBuscado = service.buscarAlumnoPorNombre(nombreAlumno);
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

    @PutMapping("/{idAlumno}/cursos")
    public ResponseEntity<?> agregarCursosAAlumno(
            @PathVariable("idAlumno") int idAlumno,
            @RequestBody List<Integer> cursosIds) {
        try {
            service.agregarCursosAAlumno(idAlumno, cursosIds);
            return ResponseEntity.status(HttpStatus.OK).body("Cursos agregados correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al agregar cursos.");
        }
    }
}
