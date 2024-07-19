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
// Los métodos de esta clase manejarán solicitudes HTTP y devolverán respuestas directamente en formato JSON (por ser RestController).
@RequestMapping("/api/alumnos")
// Define el prefijo común para todas las rutas. Se podrían aclarar en cada caso rutas individuales, pero si son los verbos tradicionales
// y el código no es muy largo, es buena práctica que apunten todos a la misma URL por distintos verbos. Si se hiciera muy grande,
// se podría separar en varios controladores de ser necesario.
public class MainAlumnoController {
    // Esta clase contendrá métodos que responderán a solicitudes HTTP (Por ser metodos de un controlador).

    @Autowired
    IAlumnoRepository repo;

    /* @RequestMapping(method = RequestMethod.GET, path = "/api/get") La anotación mapea solicitudes HTTP a métodos específicos
       Pero es nomenclatura vieja. Se incluyó en Spring y se simplificó a: @Get / Post / Put ... Mapping("/nombreRuta").
       @GetMapping("/api/get") - Evito el /api/ inicial en todas con el RequestMapping.
       @GetMapping("/get") - Evito el /get ingresando por todas las requests a la misma ruta /api, pero por distintos verbos.
    */
    @GetMapping
    public ResponseEntity<List<Alumno>> listarAlumnos() { // Es el único caso que no envío un String, porque envío el listado.
        List<Alumno> alumnos = repo.findAll();
        // Devuelve una respuesta 200 OK / HttpStatus.OK (200): La solicitud ha tenido éxito.
        return ResponseEntity.ok(alumnos); 
    }
    /* ResponseEntity: Se utiliza cuando se quiere un control explícito sobre el estado HTTP, los encabezados y el cuerpo de la respuesta.
            Spring Boot se encarga de establecer el estado HTTP a 200 OK y convertir el objeto en el cuerpo de la respuesta.
            @GetMapping("/custom")
            public ResponseEntity<String> customResponse() {
                return ResponseEntity.status(HttpStatus.CREATED)
                                     .header("Custom-Header", "CustomValue")
                                     .body("Custom response body");
            }
       De no necesitar eso, se puede utilizar una devolución directa de un objeto: Es más sencillo y se utiliza cuando no se requiere
       personalizar la respuesta HTTP. 
            @GetMapping("/alumnos")
            public List<Alumno> listarAlumnos() {
                return repo.findAll();
            }
    */

    @PostMapping
    public ResponseEntity<String> insertarAlumno(@RequestBody Alumno alumno) {
        // Devuelve una respuesta 201 Created / HttpStatus.CREATED (201): La solicitud ha tenido éxito y se ha creado un nuevo recurso.
        repo.save(alumno);
        return new ResponseEntity<>("Alumno ingresado exitosamente", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modificarAlumno(@PathVariable("id") Integer id, @RequestBody Alumno alumno) {
        // Verifico si el alumno existe.
        if (repo.existsById(id)) {
            alumno.setIdAlumno(id); // Asigna el ID proporcionado en la URL al objeto Alumno que se recibe en el cuerpo de la solicitud,
            // lo que garantiza que se esté actualizando el alumno correcto.
            repo.save(alumno); // Se usa el mismo método save, porque si lo encuentra, lo sobreescribe por completo (PUT).
            // Sin el condicional, si no encuentra el ID, lo grabaría con ID 0 u otro.
            return new ResponseEntity<>("Alumno modificado exitosamente", HttpStatus.OK);
        } else {
            // Devuelve una respuesta 404 Not Found / HttpStatus.NOT_FOUND (404): El recurso solicitado no se ha encontrado.
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
