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

/* Ejemplo JSON Alumno:

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

    /* Ventajas de manejar Logs a imprimir por consola:

    No Bloqueo del Hilo: los sistemas de logging no bloquean el hilo principal, permitiendo que la aplicación siga funcionando sin interrupciones.

    Persistencia y Configuración: Los logs pueden ser configurados para ser almacenados en archivos, bases de datos, o sistemas de monitoreo en tiempo real,
    lo que permite un análisis posterior y una supervisión continua.

    Niveles de Log: Usar niveles de log (DEBUG, INFO, WARN, ERROR, etc.) permite filtrar y gestionar la cantidad de información registrada.
    Por ejemplo, en producción, se puede querer solo ver mensajes de WARN y ERROR, mientras que en desarrollo podría estar interesado en todos los mensajes de DEBUG.

    Formateo y Estructuración: Los sistemas de logging permiten formatear los mensajes de log, agregar etiquetas, e incluso estructurar los logs en formatos JSON,
    lo que facilita su análisis automático.

    Eficiencia y Rendimiento: Aunque escribir en un archivo puede ser más lento que imprimir en consola, los sistemas de logging están optimizados para minimizar
    el impacto en el rendimiento. Además, permiten la configuración asincrónica, lo que mejora aún más el rendimiento.

    Sugerencias Adicionales:

    Rotación de Logs: Implementar un sistema que maneje la rotación de logs para evitar que los archivos de log crezcan indefinidamente, lo que puede consumir mucho espacio en disco.

    Monitoreo y Alerta: Integrar los logs con sistemas de monitoreo que puedan generar alertas en tiempo real si se detectan patrones inusuales o errores críticos.

    Contexto en los Logs: Agregar información de contexto en los logs, como identificadores de sesión o ID de usuario, puede facilitar el rastreo de problemas específicos.

    Imprimir en consola:  
    (entre otros...) Bloqueo del Hilo: Imprimir en consola generalmente es una operación sincrónica, lo que significa que el hilo que realiza la impresión se bloquea hasta
    que la operación se completa. En aplicaciones de alta concurrencia o rendimiento, este bloqueo puede convertirse en un cuello de botella.

    Desempeño en Producción: En muchos entornos de producción, la salida de la consola suele ser minimizada o completamente deshabilitada para mejorar el rendimiento
    y evitar la saturación del sistema de logs de la consola.
    */