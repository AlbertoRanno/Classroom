package ar.com.cdt.classroom.service;

import ar.com.cdt.classroom.ClassroomApplication;
import ar.com.cdt.classroom.model.Alumno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ar.com.cdt.classroom.repository.IAlumnoCustom;
import ar.com.cdt.classroom.repository.IAlumnoRepository;
import java.util.List;

@Service
@Qualifier("ServiceImplementation3")
public class AlumnoServiceImpl3 implements IAlumnoService{

    private static Logger LOG = LoggerFactory.getLogger(ClassroomApplication.class);
    
    @Autowired
    @Qualifier("CustomImplementation2")
    IAlumnoCustom repoCustom;
    
    @Autowired
    IAlumnoRepository repo;
    
    @Override
    public void registrar(String nombreAlumno) {
        repoCustom.registrar(nombreAlumno);
        LOG.info("Se usó la ServiceImplementation3");
    }

    @Override
    public List<Alumno> listarAlumnos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void guardar(Alumno alumno) {
        repo.save(alumno);
    }

    @Override
    public void eliminar(Alumno alumno) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Alumno encontrarPersona(Alumno alumno) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
}
