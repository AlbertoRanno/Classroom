package ar.com.cdt.classroom.datos;

import ar.com.cdt.classroom.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepository extends JpaRepository<Alumno, Integer> {
    
    public void registrar(String name);
}
