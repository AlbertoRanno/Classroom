package ar.com.cdt.classroom.service.impl;

import ar.com.cdt.classroom.model.Alumno;
import ar.com.cdt.classroom.repository.IAlumnoRepository;
import ar.com.cdt.classroom.service.IAlumnoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service //Lo llevan las implementaciones, no las interfaces
@Qualifier("ServiceImplementationCRUD")
public class AlumnoServiceImpl implements IAlumnoService {

    @Autowired
    IAlumnoRepository repo;

    @Override
    public ResponseEntity<List<Alumno>> listarAlumnos() {
        List<Alumno> alumnos = repo.findAll();
        return ResponseEntity.ok(alumnos);
    }

    @Override
    public ResponseEntity<String> insertarAlumno(Alumno alumno) {
        repo.save(alumno);
        return new ResponseEntity<>("Alumno ingresado exitosamente", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> modificarAlumno(Integer id, Alumno alumno) {
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

    @Override
    public ResponseEntity<String> eliminarAlumno(Integer id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return new ResponseEntity<>("Alumno eliminado existosamente", HttpStatus.OK);
        } else {
            // Devuelve una respuesta 404 Not Found / HttpStatus.NOT_FOUND (404): El recurso solicitado no se ha encontrado.
            return new ResponseEntity<>("Alumno no encontrado", HttpStatus.NOT_FOUND);
        }
    }

}
