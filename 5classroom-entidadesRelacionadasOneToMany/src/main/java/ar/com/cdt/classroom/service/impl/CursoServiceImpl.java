package ar.com.cdt.classroom.service.impl;

import ar.com.cdt.classroom.exceptions.ResourceNotFoundException;
import ar.com.cdt.classroom.model.Curso;
import ar.com.cdt.classroom.repository.ICursoRepository;
import ar.com.cdt.classroom.service.ICursoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("CursoServiceImplementationCRUD")
public class CursoServiceImpl implements ICursoService {
    
    @Autowired
    ICursoRepository repo;
    
    @Override
    public List<Curso> listarCursos() {
        List<Curso> cursos = repo.findAll();
        return cursos;
    }
    
    @Override
    public void insertarCurso(Curso curso) {
        repo.save(curso);
    }
    
    @Override
    public boolean modificarCurso(Integer id, Curso curso) {
        if (repo.existsById(id)) {
            curso.setIdCurso(id);
            repo.save(curso);
            return true;
        } else {
            return false;
            // throw new ResourceNotFoundException("Curso no encontrado");
        }
    }
    
    @Override
    public boolean eliminarCurso(Integer id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
            // throw new ResourceNotFoundException("Curso no encontrado");
        }
    }
    
}
