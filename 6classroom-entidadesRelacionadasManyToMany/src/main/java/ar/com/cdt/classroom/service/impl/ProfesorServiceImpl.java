package ar.com.cdt.classroom.service.impl;

import ar.com.cdt.classroom.exceptions.ResourceNotFoundException;
import ar.com.cdt.classroom.model.Alumno;
import ar.com.cdt.classroom.model.Curso;
import ar.com.cdt.classroom.model.Profesor;
import ar.com.cdt.classroom.repository.IAlumnoRepository;
import ar.com.cdt.classroom.repository.ICursoRepository;
import ar.com.cdt.classroom.repository.IProfesorRepository;
import ar.com.cdt.classroom.service.IProfesorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("ProfesorServiceImplementationCRUD")
public class ProfesorServiceImpl implements IProfesorService {

    @Autowired
    IProfesorRepository repo;

    @Autowired
    IAlumnoRepository repoA;

    @Autowired
    ICursoRepository repoC;

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
    
    /* Como el procedimiento implica hacer modificaciones en varias tablas, se vuelve importante el uso de transacciones, y estas deben aplicarse en la capa de serviceImpl,
    dado que acá va el código que afecta a varias tablas. De manera de que si llegó a hacer algún cambio en alguna tabla, y justo se cortó internet, o alguna tabla no se pudo 
    modificar por X error, se haga un rollback de todos los cambios, manteniendo así una premisa de "o se hace todo ok o no se hace nada". 
    Para aplicar transacciones, basta con la simple anotación de @Transactional. Que el método sea transaccional significa que, si ocurre un fallo durante cualquier parte del mismo,
    todo el proceso (modificación de tablas) se deshará automáticamente.*/
    @Transactional
    @Override /*El orden de las anotaciones por lo general es indistinto, salvo sean anotaciones personalizadas que interactuen entre sí, pero de otra forma, Spring las ejecuta
    de manera independiente*/
    public boolean eliminarProfesor(Integer id) {
        if (repo.existsById(id)) {
            /*No podía eliminar un profesor, sin antes eliminar manualmente (POSTMAN) su vínculo a un alumno.
            Intenté con (optional = true, cascade = CascadeType.DETACH, orphanRemoval = false), en uno u otro lado de la relación, y no hubo caso,
            no llegaba a eliminar el id de la relación de la tabla ajena. Por lo que opto por modificarlo desde acá. 
            Con el id del profesor, busco su objeto:    */
            Profesor profesorAEliminar = repo.findById(id).orElseThrow(); //Puedo encontrarlo o no, y al ponerlo así, evito el Optional.

            /*Desvinculo al profesor del alumno que lo tenga asignado (OneToOne). Para eso creo el metodo findByProfesor(profesor), por nomenclatura.
            Al tipear el nombre del método acá, da error, porque no existe en el repo de Alumno, pero desde el cartelito de la izquierda, puedo hacer 
            que lo cree directament ahí como los otros métodos que exceden al CRUD. */
            Alumno alumnoQueLoTeniaAsignado = repoA.findByProfesor(profesorAEliminar);

            /*Agrego un condicional, porque si no habia alumno que lo tuviera asignado, daba error*/
            if (alumnoQueLoTeniaAsignado != null) {
                alumnoQueLoTeniaAsignado.setProfesor(null);
                repoA.save(alumnoQueLoTeniaAsignado);
            }

            // Desvinculo todos los cursos asociados al profesor sino también da error
            List<Curso> cursos = profesorAEliminar.getCursos();
            for (Curso curso : cursos) {
                curso.setProfesor(null); // Elimina la relación con el profesor
                repoC.save(curso); // Guarda los cambios en el curso
            }
            /* Desvinculación de Cursos: Antes de eliminar al Profesor, se recorre la lista de Cursos asociados a él.
            Para cada Curso, se elimina la relación con el Profesor (curso.setProfesor(null)) y se guarda el cambio.
            Protección de Claves Externas: Al eliminar la relación entre el Curso y el Profesor, se evita la violación de la restricción de clave externa,
            lo que permite eliminar al Profesor sin errores */

            //Elimino al profesor
            repo.deleteById(id);
            
            //Si paso el condicional a True => Simulo un fallo lanzando una excepción
            if (false) {//Podría ser una condición real, pero no se me ocurre...
                throw new RuntimeException("Simulación de fallo después de eliminar al profesor");
            }
            
            //Si llegara aquí (cosa que con el condicional en True no pasaría, dada la excepción), la transacción se completaría.
            
            //El concepto de transacciones podría profundizarse con Propagación, Aislamiento, transacciones anidadas... pero me excede de momento.
            
            return true;
        } else {
            return false;
            // throw new ResourceNotFoundException("Alumno no encontrado -Impl");
        }
    }

}
