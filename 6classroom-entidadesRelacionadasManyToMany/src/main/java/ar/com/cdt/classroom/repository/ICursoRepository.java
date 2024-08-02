package ar.com.cdt.classroom.repository;

import ar.com.cdt.classroom.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICursoRepository extends JpaRepository<Curso, Integer> {
    
}
