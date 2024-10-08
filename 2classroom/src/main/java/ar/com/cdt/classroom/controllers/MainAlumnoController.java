package ar.com.cdt.classroom.controllers;

import ar.com.cdt.classroom.model.Alumno;
import ar.com.cdt.classroom.service.IAlumnoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("ServiceImplementationCRUD") //No necesario por haber solo una implementacion pero lo queria recordar
    IAlumnoService service;

    @GetMapping
    public ResponseEntity<List<Alumno>> listarAlumnos() {
        // return service.listarAlumnos();
        return ResponseEntity.ok(service.listarAlumnos());
        //Sería la versión de:  ResponseEntity.ok(alumnos); 
    }

    @PostMapping
    public ResponseEntity<String> insertarAlumno(@RequestBody Alumno alumno) {
        // return service.insertarAlumno(alumno);
        service.insertarAlumno(alumno);
        return new ResponseEntity<>("Alumno ingresado exitosamente", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modificarAlumno(@PathVariable("id") Integer id, @RequestBody Alumno alumno) {
        //return service.modificarAlumno(id, alumno);
        if (service.modificarAlumno(id, alumno)) {
            return new ResponseEntity<>("Alumno modificado exitosamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Alumno no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarAlumno(@PathVariable("id") Integer id) {
        //return service.eliminarAlumno(id);
        if (service.eliminarAlumno(id)) {
            return new ResponseEntity<>("Alumno eliminado existosamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Alumno no encontrado", HttpStatus.NOT_FOUND);
        }
    }
}

/*@RequestBody: Esta anotación indica que un parámetro del método debe ser llenado con el contenido del cuerpo de la solicitud HTTP. 
Se utiliza en el controlador para deserializar el JSON o XML recibido en un objeto Java. 
Este proceso es específico de la capa web, ya que es donde las solicitudes HTTP son manejadas.
Por ende, es mejor, si bien funciona, no repetir la anotación en la capa de servicio.

@PathVariable: Esta anotación se usa para extraer valores de la URL de la solicitud.
Es útil en los controladores para acceder a partes variables de la ruta de la solicitud.
Lo mismo, por ende, es mejor, si bien funciona, no repetir la anotación en la capa de servicio.
 */
