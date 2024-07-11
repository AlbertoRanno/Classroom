package ar.com.cdt.classroom.service;

import ar.com.cdt.classroom.ClassroomApplication;
import ar.com.cdt.classroom.repository.IAlumnoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("ServiceImplementation1")
public class AlumnoServiceImpl1 implements IAlumnoService{

    private static Logger LOG = LoggerFactory.getLogger(ClassroomApplication.class);
    
    @Autowired
    @Qualifier("RepositoryImplementation1")
    IAlumnoRepository repo;
    
    @Override
    public void registrar(String name) {
        repo.registrar(name);
        LOG.info("Se usó la ServiceImplementation1");
    }
    
}
