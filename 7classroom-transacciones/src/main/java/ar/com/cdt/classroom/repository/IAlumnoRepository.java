package ar.com.cdt.classroom.repository;

import ar.com.cdt.classroom.model.Alumno;
import ar.com.cdt.classroom.model.Profesor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IAlumnoRepository extends JpaRepository<Alumno, Integer> {

    Alumno findByNombreAlumno(String nombreAlumno);

    List<Alumno> findByProfesorIsNull();

    @Query("SELECT a FROM Alumno a WHERE a.nombreAlumno = :nombre")
    Alumno buscarAlumnoPorNombre(@Param("nombre") String nombreAlumno);

    public Alumno findByProfesor(Profesor profesor);
}