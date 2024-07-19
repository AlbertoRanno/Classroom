package ar.com.cdt.classroom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ar.com.cdt.classroom.servicecustom.IAlumnoCustomService;

@SpringBootApplication
public class ClassroomApplication implements CommandLineRunner{
    //Implemento, sobreescribiendo su unico metodo, run, para usar los LOGs, que tienen mas PROS que imprimir en consola

    //Defino el log:
    private static Logger LOG = LoggerFactory.getLogger(ClassroomApplication.class);
    
    @Autowired
    @Qualifier("ServiceImplementation3")
    IAlumnoCustomService service;
        
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
        
        service.registrar("Alberto Daniel Ranno");
    }
    
    
    

}
