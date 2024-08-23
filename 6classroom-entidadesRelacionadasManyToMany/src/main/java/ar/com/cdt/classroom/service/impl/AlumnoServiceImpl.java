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

@Service
@Qualifier("ServiceImplementationCRUD") 
//De tener que elegir ésta, de entre varias interfaces, cuando hago el autowired en el controlador, mediante @Qualifier("ServiceImplementationCRUD") la llamo
public class AlumnoServiceImpl implements IAlumnoService {

    @Autowired
    IAlumnoRepository repo;

    @Autowired
    ICursoRepository repoC;

    //Lo mismo que en la interfaz, lo mantengo simple, dejo cosas como el manejo del ResponseEntity, del lado del controlador, que es realmente donde se necesitan
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
    
    /* El siguiente es un metodo que afecta dos entidades, por eso no está en el JpaRepository, sino en la Impl de Serv.
    Tuve que agregar las línes de: .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado"));
    porque findById devuelve un Optional, es decir, que lo puede encontrar, y devolver la entidad o no..
    Si la devuelve, la asocia a la variable, y sigue normal, sino, el error, corta el hilo con un mensaje. */

    @Override
    public void agregarCursosAAlumno(int idAlumno, List<Integer> cursosIds) {
        // Busco al alumno que se va a inscribir
        Alumno alumnoAInscribir = repo.findById(idAlumno)
                .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado"));
        
        // Borro la lista actual de cursos del alumno
        alumnoAInscribir.getCursos().clear(); //De no haber estado esta línea, siempre se agregaban... 

        // Añado los nuevos cursos
        for (int idCurso : cursosIds) { //Recorro la lista de IDs que se envían en el body por Postman
            // Busco el curso donde se va a inscribir, que corresponde a ese ID
            Curso curso = repoC.findById(idCurso)
                    .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));
            // Agrego el curso a la lista de cursos del alumno
            alumnoAInscribir.getCursos().add(curso);
            //getCursos => Gracias a private List<Curso> cursos = new ArrayList<>();
            //alumnoAInscribir.getCursos() => Devuelve la Lista de cursos
            //add, es un metodo de List, para agregarle el curso a la Lista
        }
        
        // Guardo los cambios en el repositorio de alumnos
        repo.save(alumnoAInscribir);
    }

}