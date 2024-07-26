package ar.com.cdt.classroom.service.impl;

import ar.com.cdt.classroom.exceptions.ResourceNotFoundException;
import ar.com.cdt.classroom.model.Profesor;
import ar.com.cdt.classroom.repository.IProfesorRepository;
import ar.com.cdt.classroom.service.IProfesorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("ProfesorServiceImplementationCRUD")
public class ProfesorServiceImpl implements IProfesorService {

    @Autowired
    IProfesorRepository repo;

    @Override
    public List<Profesor> listarProfesores() {
        List<Profesor> profesores = repo.findAll();
        return profesores;
    }

    @Override
    public void insertarProfesor(Profesor profesor) {
        repo.save(profesor);
    }

    @Override
    public boolean modificarProfesor(Integer id, Profesor profesor) {
        if (repo.existsById(id)) {
            profesor.setIdProfesor(id);
            repo.save(profesor);
            return true;
        } else {
            return false;
            // throw new ResourceNotFoundException("Alumno no encontrado -Impl");
        }
    }

    @Override
    public boolean eliminarProfesor(Integer id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
             return false;
            // throw new ResourceNotFoundException("Alumno no encontrado -Impl");
        }
    }

}
