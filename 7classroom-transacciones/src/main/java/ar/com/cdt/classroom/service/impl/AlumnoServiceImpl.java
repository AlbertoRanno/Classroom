package ar.com.cdt.classroom.service.impl;

import ar.com.cdt.classroom.exceptions.ResourceNotFoundException;
import ar.com.cdt.classroom.model.Alumno;
import ar.com.cdt.classroom.model.Curso;
import ar.com.cdt.classroom.repository.IAlumnoRepository;
import ar.com.cdt.classroom.repository.ICursoRepository;
import ar.com.cdt.classroom.service.IAlumnoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("ServiceImplementationCRUD")
public class AlumnoServiceImpl implements IAlumnoService {

    @Autowired
    IAlumnoRepository repo;

    @Autowired
    ICursoRepository repoC;

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
            // throw new ResourceNotFoundException("Alumno no encontrado -Impl");
        }
    }

    @Override
    public boolean eliminarAlumno(Integer id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        } else {
            return false;
            //throw new ResourceNotFoundException("Alumno no encontrado -Impl");
        }
    }

    @Override
    public Alumno findByNombre(String nombreAlumno) {
        Alumno alumnoBuscado = repo.findByNombreAlumno(nombreAlumno);
        return alumnoBuscado;
    }

    @Override
    public List<Alumno> findByProfesorIsNull() {
        List<Alumno> alumnosSinProfe = repo.findByProfesorIsNull();
        return alumnosSinProfe;
    }

    @Override
    public Alumno buscarAlumnoPorNombre(String nombreAlumno) {
        Alumno alumnoBuscado = repo.buscarAlumnoPorNombre(nombreAlumno);
        return alumnoBuscado;
    }

    /* En 1a1 Jackson permite editar la relación directamente del PUT, pero en Muchos a Muchos, No 
    (dado que tendría que manipular, a diferencia de la relación 1a1, una tabla intermedia),
    por lo que hace falta un método específico: */
    @Transactional
    @Override
    public void agregarCursosAAlumno(int idAlumno, List<Integer> cursosIds) {
        Alumno alumnoAInscribir = repo.findById(idAlumno)
                .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado"));

        alumnoAInscribir.getCursos().clear();

        for (int idCurso : cursosIds) {
            Curso curso = repoC.findById(idCurso)
                    .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));
            alumnoAInscribir.getCursos().add(curso);
        }
        
        // Si lo paso a True, entra al catch del controlador, arroja su msj y se hace el rollback
        if (false) {
            throw new RuntimeException("Excepción de prueba para cortar la transacción en el método de agregar cursos a un alumno");
        }
        
        repo.save(alumnoAInscribir);
    }
}