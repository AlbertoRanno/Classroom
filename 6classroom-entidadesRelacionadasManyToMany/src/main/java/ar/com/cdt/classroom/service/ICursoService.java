package ar.com.cdt.classroom.service;

import ar.com.cdt.classroom.model.Curso;
import java.util.List;

public interface ICursoService {

    List<Curso> listarCursos();

    void insertarCurso(Curso curso);

    boolean modificarCurso(Integer id, Curso curso);

    boolean eliminarCurso(Integer id);
}
