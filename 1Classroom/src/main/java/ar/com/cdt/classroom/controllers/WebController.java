package ar.com.cdt.classroom.controllers;

import ar.com.cdt.classroom.model.Alumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ar.com.cdt.classroom.servicecustom.IAlumnoCustomService;

@Controller
public class WebController {
    
    @Autowired
    @Qualifier("ServiceImplementation1")
    IAlumnoCustomService service;

    @GetMapping("/inicio")
    public String inicio(@RequestParam(name = "usuario", required = false, defaultValue = "usuario anónimo") String nombreUsuario, Model model) {
        model.addAttribute("nombreUsuario", nombreUsuario);
        return "index";
    }

    @GetMapping("/guardarAlumno")
    public String guardarAlumno(Model model) {
        
        Alumno alumno = new Alumno();
        alumno.setIdAlumno(1);
        alumno.setNombreAlumno("Julio");
        alumno.setApellidoAlumno("Cesar");
        alumno.setTelefonoAlumno("11224455");
        alumno.setEmailAlumno("jcesar@mail.com");
        //service.guardar(alumno); //Lo comenté porque separé las interfaces de pruebas de las que hacen el CRUD
        
        return "listado";
    }
}
