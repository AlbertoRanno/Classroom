package ar.com.cdt.classroom.servicecustom.impl;

import ar.com.cdt.classroom.ClassroomApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ar.com.cdt.classroom.repositorycustom.IAlumnoCustom;
import ar.com.cdt.classroom.servicecustom.IAlumnoCustomService;

@Service
@Qualifier("ServiceImplementation1")
public class AlumnoCustomServiceImpl1 implements IAlumnoCustomService{

    private static Logger LOG = LoggerFactory.getLogger(ClassroomApplication.class);
    
    @Autowired
    @Qualifier("CustomImplementation1")
    IAlumnoCustom repo;
    
    @Override
    public void registrar(String nombreAlumno) {
        repo.registrar(nombreAlumno);
        LOG.info("Se usó la ServiceImplementation1");
    }

}
