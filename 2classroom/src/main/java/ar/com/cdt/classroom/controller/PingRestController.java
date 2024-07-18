package ar.com.cdt.classroom.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
//Los métodos de esta clase manejarán solicitudes HTTP y devolverán respuestas directamente en formato JSON
public class PingRestController {
//Esta clase contendrá métodos que responderán a solicitudes HTTP.

    // Método que maneja solicitudes GET a la ruta "/api/ping"
    @RequestMapping(method = RequestMethod.GET, path = "/api/ping")
    //Esta anotación se utiliza para mapear solicitudes HTTP a métodos específicos dentro del controlador.
    //method = RequestMethod.GET: Indica que este método manejará solicitudes HTTP GET.
    //path = "/api/ping": Especifica la URL a la que este método responderá. En este caso, el método responderá a solicitudes GET enviadas a "/api/ping".
    public ResponseEntity<String> getPing() {
    //Declara un método público llamado getPing que devuelve un objeto de tipo ResponseEntity<String>. 
    //ResponseEntity es una clase que representa toda la respuesta HTTP, incluyendo el estado y el cuerpo.

        // Este código crea una respuesta HTTP con el estado 200 OK y el cuerpo de la respuesta es el string "pong".
        return ResponseEntity.ok("pong");
    }

}
