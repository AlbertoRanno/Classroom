package ar.com.cdt.classroom.repository;

import ar.com.cdt.classroom.model.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IProfesorRepository extends JpaRepository<Profesor, Integer> {

//@Query
/*Listas ordenadas u otro metodo en particular
    muchos a muchos
    transacciones
    swagger
    */
    
    
}
