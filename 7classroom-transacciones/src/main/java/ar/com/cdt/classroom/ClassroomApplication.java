package ar.com.cdt.classroom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClassroomApplication implements CommandLineRunner {

    //Defino el log:
    private static Logger LOG = LoggerFactory.getLogger(ClassroomApplication.class);
    //Implemento, sobreescribiendo su unico metodo, run, para usar los LOGs, que tienen mas PROS que imprimir en consola

    //Metodo principal de la app
    public static void main(String[] args) {
        SpringApplication.run(ClassroomApplication.class, args);
    }
    
    //Sobreescribo el metodo run para la app
    @Override
    public void run(String... args) throws Exception {
        LOG.info("INFO");
        LOG.warn("WARN");
        LOG.error("ERROR");
        //Depende el IDE se ven en diferentes colores, pero aca no...
    }
}

/*

{
        "idAlumno": 2,
        "nombreAlumno": "Alberto",
        "apellidoAlumno": "Ranno",
        "telefonoAlumno": "4499-2266",
        "emailAlumno": "aranno.mail.com",
        "profesor": {
            "idProfesor": 1,
            "nombreProfesor": "Julio",
            "apellidoProfesor": "Cesar",
            "telefonoProfesor": "6699-3266",
            "asignaturaProfesor": "Historia",
            "antiguedadProfesor": 30
        },
        "cursos": [
            {
                "idCurso": 1,
                "nombreCurso": "Álgebra I",
                "descripcion": "Curso básico de álgebra",
                "profesor": {
                    "idProfesor": 1,
                    "nombreProfesor": "Julio",
                    "apellidoProfesor": "Cesar",
                    "telefonoProfesor": "6699-3266",
                    "asignaturaProfesor": "Historia",
                    "antiguedadProfesor": 30
                }
            },
            {
                "idCurso": 2,
                "nombreCurso": "Física III",
                "descripcion": "Curso avanzado de física",
                "profesor": {
                    "idProfesor": 1,
                    "nombreProfesor": "Julio",
                    "apellidoProfesor": "Cesar",
                    "telefonoProfesor": "6699-3266",
                    "asignaturaProfesor": "Historia",
                    "antiguedadProfesor": 30
                }
            }
        ]
    }

*/