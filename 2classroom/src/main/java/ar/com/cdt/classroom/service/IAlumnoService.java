package ar.com.cdt.classroom.service;

import ar.com.cdt.classroom.model.Alumno;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAlumnoService {

    ResponseEntity<List<Alumno>> listarAlumnos();

    ResponseEntity<String> insertarAlumno(@RequestBody Alumno alumno);

    ResponseEntity<String> modificarAlumno(@PathVariable("id") Integer id, @RequestBody Alumno alumno);

    ResponseEntity<String> eliminarAlumno(@PathVariable("id") Integer id);
}
