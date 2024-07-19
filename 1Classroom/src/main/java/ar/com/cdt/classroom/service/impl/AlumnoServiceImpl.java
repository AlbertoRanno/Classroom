package ar.com.cdt.classroom.service.impl;

import ar.com.cdt.classroom.service.IAlumnoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service //Lo llevan las implementaciones, no las interfaces
@Qualifier("ServiceImplementationCRUD")
public class AlumnoServiceImpl implements IAlumnoService {
    
}
