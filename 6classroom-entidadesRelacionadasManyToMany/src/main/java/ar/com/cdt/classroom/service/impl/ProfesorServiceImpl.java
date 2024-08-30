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

    @Override
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
            return true;
        } else {
            return false;
            // throw new ResourceNotFoundException("Alumno no encontrado -Impl");
        }
    }

}
