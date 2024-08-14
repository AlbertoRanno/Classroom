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
    IAlumnoService service;

    @Autowired
    ICursoService serviceC;

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
    public ResponseEntity<?> findByNombre(@PathVariable("nombreAlumno") String nombreAlumno) {
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

    /* Este endpoint fue específicamente diseñado para agregar cursos a un alumno. Espera que se envíe una lista de IDs de cursos en el cuerpo de la solicitud por medio de POstman.
    Llamo a agregarCursosAAlumno y le paso el ID del alumno y la lista de IDs de cursos. Se envía una respuesta adecuada dependiendo de si la operación fue exitosa o falló.

    Luego de establecer la relación muchos a muchos, intenté usar el endpoint de actualizar alumno para agregarle los cursos, pero por más que probé de varias formas (con
    los objetos curso completos, con solo sus ids,...) siempre me dió errores. Para evitar problemas es que se usa un endpoint, propio, con métodos para manejar las relaciones
    muchos a muchos. Es una práctica recomendada. Estos problemas suelen surgir debido a que Jackson puede tener problemas cuando intenta manejar objetos complejos con relaciones
    bidireccionales o IDs ya existentes. Tener un endpoint específico permite trabajar con datos más simples, como listas de IDs, y manejar las relaciones de manera explícita 
    en el código de servicio.
    También permite que las operaciones en las relaciones se manejen dentro de una transacción, lo que garantiza que si una parte de la operación falla, se reviertan todos 
    los cambios.*/
