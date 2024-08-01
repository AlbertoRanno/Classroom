package ar.com.cdt.classroom.controllers;

import ar.com.cdt.classroom.model.Curso;
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
@RequestMapping("/api/cursos")
public class MainCursoController {

    @Autowired
    ICursoService service;

    @GetMapping
    public ResponseEntity<List<Curso>> listarcursos() {
        List<Curso> cursos = service.listarCursos();
        return ResponseEntity.ok(cursos);
    }

    @PostMapping
    public ResponseEntity<String> insertarCurso(@RequestBody Curso curso) {
        service.insertarCurso(curso);
        return new ResponseEntity<>("Curso ingresado exitosamente", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modificarCurso(@PathVariable("id") Integer id, @RequestBody Curso curso){
        if (service.modificarCurso(id, curso)) {
            return new ResponseEntity<>("Curso modificado exitosamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Curso no encontrado -Controlador", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCurso(@PathVariable("id") Integer id){
        if (service.eliminarCurso(id)) {
            return new ResponseEntity<>("Curso eliminado exitosamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Curso no encontrado -Controlador", HttpStatus.NOT_FOUND);
        }
    }
    

}
