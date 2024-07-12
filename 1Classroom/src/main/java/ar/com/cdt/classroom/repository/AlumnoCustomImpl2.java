package ar.com.cdt.classroom.repository;

import ar.com.cdt.classroom.ClassroomApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("CustomImplementation2")
public class AlumnoCustomImpl2 implements IAlumnoCustom{

    private static Logger LOG = LoggerFactory.getLogger(ClassroomApplication.class);
    
    @Override
    public void registrar(String nombreAlumno) {
        LOG.info("Se registró al alumno " + nombreAlumno + " mediante la CustomImplementation2");
    }
    
}
