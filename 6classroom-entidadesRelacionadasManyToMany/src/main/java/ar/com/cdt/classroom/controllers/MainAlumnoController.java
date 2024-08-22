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
// Los métodos de esta clase manejarán solicitudes HTTP y devolverán respuestas directamente en formato JSON (por ser RestController).
@RequestMapping("/api/alumnos") //Es Request (pedido) Mapping => GetMapping es para el verbo, acordate! 
// Define el prefijo común para todas las rutas. Se podrían aclarar en cada caso rutas individuales, pero si son los verbos tradicionales
// y el código no es muy largo, es buena práctica que apunten todos a la misma URL por distintos verbos. Si se hiciera muy grande,
// se podría separar en varios controladores de ser necesario.
public class MainAlumnoController {
    // Esta clase contendrá métodos que responderán a solicitudes HTTP (Por ser metodos de un controlador).

    @Autowired //Inyecta automáticamente una dependencia en el controlador. Evita escribir:   IAlumnoService service = new AlumnoServiceImpl();
    //Acá se conecta con la interfaz, de modo de poder swichear implementaciones falcilmente. Es altamente desacoplable.
    //@Qualifier("ServiceImplementationCRUD") De tener que elegir la interfaz, uso esa anotación
    //Sin el @Qualifier, Spring buscará una implementación disponible de IAlumnoService y la inyectará automáticamente.
    IAlumnoService service;
    

    /* @RequestMapping(method = RequestMethod.GET, path = "/api/get") La anotación mapea solicitudes HTTP a métodos específicos
       Pero es nomenclatura vieja. Se incluyó en Spring y se simplificó a: @Get / Post / Put ... Mapping("/nombreRuta").
       @GetMapping("/api/get") - Evito el /api/ inicial en todas con el RequestMapping.
       @GetMapping("/get") - Evito el /get ingresando por todas las requests a la misma ruta /api, pero por distintos verbos.
    */

    @Autowired
    ICursoService serviceC;

    @GetMapping
    public ResponseEntity<List<Alumno>> listarAlumnos() { // Es el único caso que no envío un String, porque envío el listado.
        //return ResponseEntity.ok(service.listarAlumnos());
        return ResponseEntity.status(HttpStatus.OK)
                .header("Custom-Header", "CustomValue")
                .body(service.listarAlumnos());
    }
    
    /* ResponseEntity: Es una clase propia de Spring. Se utiliza cuando se quiere un control explícito sobre el estado HTTP 
    ( 200 OK, 201 Created, 404 Not Found, 500 Internal Server Error, etc. Esto es útil para indicar al cliente si la operación fue exitosa o si ocurrió un error),
    los encabezados (se pueden configurar cosas de seguridad, control de caché, u otra info adicional) y el cuerpo de la respuesta (envío un Objeto, que Jackson pasará a JSON).
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
        /*@RequestBody: Esta anotación indica que un parámetro del método debe ser llenado con el contenido del cuerpo de la solicitud HTTP. 
        Se utiliza en el controlador para deserializar el JSON o XML recibido en un objeto Java. 
        Este proceso es específico de la capa web, ya que es donde las solicitudes HTTP son manejadas.
        Por ende, es mejor, si bien funciona, no repetir la anotación en la capa de servicio.   */
        service.insertarAlumno(alumno);
        //return new ResponseEntity<>("Alumno ingresado exitosamente", HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED) // Devuelve una respuesta 201 Created / HttpStatus.CREATED (201): La solicitud ha tenido éxito y se ha creado un nuevo recurso.
                .header("Custom-Header", "CustomValue")
                .body("Alumno ingresado exitosamente -FD");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modificarAlumno(@PathVariable("id") Integer id, @RequestBody Alumno alumno) {
        /*    @PathVariable: Esta anotación se usa para extraer valores de la URL de la solicitud.
        Es útil en los controladores para acceder a partes variables de la ruta de la solicitud.
        Lo mismo, por ende, es mejor, si bien funciona, no repetir la anotación en la capa de servicio.  */
        if (service.modificarAlumno(id, alumno)) {
            // return new ResponseEntity<>("Alumno modificado exitosamente", HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Custom-Header", "CustomValue")
                    .body("Alumno modificado exitosamente -FD");
        } else {
            // return new ResponseEntity<>("Alumno no encontrado -Controler", HttpStatus.NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND) // Devuelve una respuesta 404 Not Found / HttpStatus.NOT_FOUND (404): El recurso solicitado no se ha encontrado.
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
