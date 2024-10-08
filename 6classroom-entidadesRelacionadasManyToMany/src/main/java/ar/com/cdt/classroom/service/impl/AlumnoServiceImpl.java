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
//De tener que elegir ésta, de entre varias implementaciones, cuando hago el autowired en el controlador, mediante @Qualifier("ServiceImplementationCRUD") la llamo
public class AlumnoServiceImpl implements IAlumnoService {

    //Cuando los metodos son complejos y/o involucran cruces entre tablas, las implementaciones de la capa de servicio son el lugar ideal para desarrollarlos.
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
    
    /*
    Este método es especial y no está en JpaRepository, sino en la implementación del servicio.
    Su propósito es añadir cursos a un alumno, y para lograrlo se necesita un método propio.
    La razón principal es que, al intentar gestionar esta relación muchos a muchos (Alumno y Curso) desde
    un PUT genérico, Jackson podría generar errores debido a la complejidad de manejar la tabla intermedia que
    almacena esta relación. A diferencia de la relación 1 a 1 entre Profesor y Alumno, donde Jackson puede manejar
    las asociaciones directamente, las relaciones muchos a muchos requieren un manejo explícito. 
    Por eso, es necesario un método específico como este para evitar problemas de serialización/deserialización.
    
    Tuve que agregar las líneas de: .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado"));
    porque findById devuelve un Optional, es decir, que lo puede encontrar, y devolver la entidad o no..
    Si la devuelve, la asocia a la variable, y sigue normal, sino, el error, corta el hilo con un mensaje. 

    getCursos() trae una List, y clear y add son métodos que pertenecen a la lista de cursos (List<Curso>) 
    */
    
    //Metodo especial
    @Transactional //***
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
        
        // Quise lanzar la siguiente excepción, para probar el rollback en este medoto transactional, y si la misma la dejo en true, funciona, se hace el rollback:
        if (false) {
            throw new RuntimeException("Excepción de prueba para cortar la transacción en el método de agregar cursos a un alumno");
        }
        /* Lo que sí, No se ve este mensaje de error, sino el mensaje: 'Error al agregar cursos.' 
        Esto sucede ya que el codigo que puse en el controlador, tiene un try-catch, por lo que al lanzar adrede una excepción acá la captura el catch y lanza su msj:
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
        De haber manejado el controlador sin el try-catch, se hubiese lanzado ese mensaje.  */
        
        // Guardo los cambios en el repositorio de alumnos
        repo.save(alumnoAInscribir);
    }
    
    /***    TRANSACCION
    Una transacción en programación es un conjunto de operaciones que deben ejecutarse de manera completa o no ejecutarse en absoluto. 
    Si una parte falla, se deshace todo lo que se haya hecho hasta ese punto, garantizando la integridad de los datos.

    La anotación @Transactional en Spring gestiona esto automáticamente. 
    Marca un método o clase para que todas las operaciones dentro de él se ejecuten como una única transacción. 
    Si alguna operación falla, se realiza un rollback (se revierten los cambios).   */
}