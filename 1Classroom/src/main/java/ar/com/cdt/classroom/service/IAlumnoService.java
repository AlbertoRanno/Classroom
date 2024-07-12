package ar.com.cdt.classroom.service;

import ar.com.cdt.classroom.model.Alumno;
import java.util.List;

public interface IAlumnoService {

    void registrar(String nombreAlumno);

    public List<Alumno> listarAlumnos();

    public void guardar(Alumno alumno);

    public void eliminar(Alumno alumno);

    public Alumno encontrarPersona(Alumno alumno);
}
