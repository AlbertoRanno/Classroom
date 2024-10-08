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

@Service
@Qualifier("ServiceImplementationCRUD")
public class AlumnoServiceImpl implements IAlumnoService {

    @Autowired
    IAlumnoRepository repo;

    @Override
    public List<Alumno> listarAlumnos() {
        List<Alumno> alumnos = repo.findAll();
        return alumnos;
    }

    @Override
    //public ResponseEntity<String> insertarAlumno(Alumno alumno) {
    public void insertarAlumno(Alumno alumno) {
        repo.save(alumno);
        // return new ResponseEntity<>("Alumno ingresado exitosamente", HttpStatus.CREATED);
    }

    @Override
    //public ResponseEntity<String> modificarAlumno(Integer id, Alumno alumno) {
    //public void modificarAlumno(Integer id, Alumno alumno) {
    public boolean modificarAlumno(Integer id, Alumno alumno) {
        if (repo.existsById(id)) {
            alumno.setIdAlumno(id);
            repo.save(alumno);
            //return new ResponseEntity<>("Alumno modificado exitosamente", HttpStatus.OK);
            // } else {
            // return new ResponseEntity<>("Alumno no encontrado", HttpStatus.NOT_FOUND);
            //  }
            return true;  // Indica que la modificación fue exitosa
        } else {
            return false; // Indica que el alumno no fue encontrado
        }
    }

    @Override
    //public ResponseEntity<String> eliminarAlumno(Integer id) {
    public boolean eliminarAlumno(Integer id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            /* return new ResponseEntity<>("Alumno eliminado existosamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Alumno no encontrado", HttpStatus.NOT_FOUND);
        } */
            return true;
        } else {
            return false;
        }
    }
}
