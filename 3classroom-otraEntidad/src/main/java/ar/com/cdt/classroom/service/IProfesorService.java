package ar.com.cdt.classroom.service;

import ar.com.cdt.classroom.model.Profesor;
import java.util.List;

public interface IProfesorService {
    
    List<Profesor> listarProfesores();
    
    void insertarProfesor(Profesor profesor);
    
    boolean modificarProfesor(Integer id, Profesor profesor);
    
    boolean eliminarProfesor(Integer id);
}
