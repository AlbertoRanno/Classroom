package ar.com.cdt.classroom.service;

import ar.com.cdt.classroom.model.Alumno;
import java.util.List;

public interface IAlumnoService {

    List<Alumno> listarAlumnos();

    void insertarAlumno(Alumno alumno);

    boolean modificarAlumno(Integer id, Alumno alumno);

    boolean eliminarAlumno(Integer id);
}