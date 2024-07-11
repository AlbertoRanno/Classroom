package ar.com.cdt.classroom.repository;

import ar.com.cdt.classroom.ClassroomApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("RepositoryImplementation1")
public class AlumnoRepositoryImpl1 implements IAlumnoRepository{

    private static Logger LOG = LoggerFactory.getLogger(ClassroomApplication.class);
    
    @Override
    public void registrar(String name) {
        LOG.info("Se registró al alumno " + name + "mediante la RepositoryImplementation1");
    }
    
}
