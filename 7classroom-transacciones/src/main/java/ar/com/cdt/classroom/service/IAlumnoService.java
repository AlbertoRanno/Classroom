package ar.com.cdt.classroom.service;

import ar.com.cdt.classroom.model.Alumno;
import java.util.List;

public interface IAlumnoService {
    
    //Es mas flexible la interface, y por ende las implementaciones, si aquí mantengo el código lo más sencillo posible,
    //y dejo, por ejempli el manejo de ResposeEntity solo al controlador, que es donde se necesitan realmente.
    List<Alumno> listarAlumnos();

    void insertarAlumno(Alumno alumno);

    boolean modificarAlumno(Integer id, Alumno alumno);

    boolean eliminarAlumno(Integer id);

    Alumno findByNombre(String nombreAlumno);

    List<Alumno> findByProfesorIsNull();

    Alumno buscarAlumnoPorNombre(String nombreAlumno);

    void agregarCursosAAlumno(int idAlumno, List<Integer> cursosIds);

}
