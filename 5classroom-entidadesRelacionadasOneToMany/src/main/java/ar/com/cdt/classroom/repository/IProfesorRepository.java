package ar.com.cdt.classroom.repository;

import ar.com.cdt.classroom.model.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProfesorRepository extends JpaRepository<Profesor, Integer> {
    
}
