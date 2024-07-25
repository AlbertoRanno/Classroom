package ar.com.cdt.classroom.service;

import ar.com.cdt.classroom.model.Alumno;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAlumnoService {

    //ResponseEntity<List<Alumno>> listarAlumnos();
    List<Alumno> listarAlumnos();

    //ResponseEntity<String> insertarAlumno(@RequestBody Alumno alumno); 
    //Es mas flexible la interface, y por ende la implementacion, si delego las anotaciones de este tipo, solo al controlador, que es donde se necesitan realmente.
    //ResponseEntity<String> insertarAlumno(Alumno alumno);
    //String insertarAlumno(Alumno alumno); Si hicera esto, en la implementacion tengo que devolver un string, pero prefiero en la implementacion no devolver nada (void)
    //Y en el controlador, enviar el string, junto con el status, mediante la clase ResponseEntity
    void insertarAlumno(Alumno alumno);

    //ResponseEntity<String> modificarAlumno(@PathVariable("id") Integer id, @RequestBody Alumno alumno);
    //ResponseEntity<String> modificarAlumno(Integer id, Alumno alumno);
    //void modificarAlumno(Integer id, Alumno alumno); //quiero analizar si es posible encontrar y modificar el registro del alumno o no... 
    boolean modificarAlumno(Integer id, Alumno alumno);

    //ResponseEntity<String> eliminarAlumno(@PathVariable("id") Integer id);
    //ResponseEntity<String> eliminarAlumno(Integer id);
     boolean eliminarAlumno(Integer id);
}
