package ar.com.cdt.classroom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClassroomApplication implements CommandLineRunner {

    private static Logger LOG = LoggerFactory.getLogger(ClassroomApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ClassroomApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("INFO");
        LOG.warn("WARN");
        LOG.error("ERROR");
    }
}
