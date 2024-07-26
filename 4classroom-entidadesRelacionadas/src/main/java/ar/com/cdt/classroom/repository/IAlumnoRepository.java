package ar.com.cdt.classroom.repository;

import ar.com.cdt.classroom.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAlumnoRepository extends JpaRepository<Alumno, Integer> {

}
