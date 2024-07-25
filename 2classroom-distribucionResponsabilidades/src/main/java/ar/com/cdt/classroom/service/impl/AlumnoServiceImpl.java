package ar.com.cdt.classroom.service.impl;

import ar.com.cdt.classroom.exceptions.ResourceNotFoundException;
import ar.com.cdt.classroom.model.Alumno;
import ar.com.cdt.classroom.repository.IAlumnoRepository;
import ar.com.cdt.classroom.service.IAlumnoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public void insertarAlumno(Alumno alumno) {
        repo.save(alumno);
    }

    @Override
    public boolean modificarAlumno(Integer id, Alumno alumno) {
        if (repo.existsById(id)) {
            alumno.setIdAlumno(id);
            repo.save(alumno);
            return true;
        } else {
            return false;
           //throw new ResourceNotFoundException("Alumno no encontrado -Impl");
           // si no estuviera comentada la excepción, la misma corta el flujo de la app, y arroja el msj de error a postman
        }
    }
    
    @Override
    public boolean eliminarAlumno(Integer id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
