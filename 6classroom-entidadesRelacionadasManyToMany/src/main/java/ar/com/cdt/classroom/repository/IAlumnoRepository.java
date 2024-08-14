package ar.com.cdt.classroom.repository;

import ar.com.cdt.classroom.model.Alumno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IAlumnoRepository extends JpaRepository<Alumno, Integer> {

    // Métodos basados en convenciones de nombres de JPA
    Alumno findByNombreAlumno(String nombreAlumno);

    /* teoricamente, necesita ser findByNombreAlumnoIgnoreCase, para que hiciera caso omiso de Mayus y Minus, pero así veo que funciona igual.
    Ojo que el nombre tiene que ser tal cual la propiedad del Modelo (no funciona findByNombre)
    Y respetar la convención establecida para las mayúsculas, no funciona findBynombreAlumno */

    List<Alumno> findByProfesorIsNull();
    //findBy: Indica que quiero realizar una consulta de búsqueda.
    //ProfesorIsNull: Especifica que estoy buscando alumnos cuyo atributo profesor es null, es decir, no lo tienen asignado

    // Método equivalente usando @Query
    @Query("SELECT a FROM Alumno a WHERE a.nombreAlumno = :nombre")
    Alumno buscarPorNombre(@Param("nombre") String nombreAlumno);
}

/* 
    Como crear un metodo, distinto, a los que ofrece JpaRepository. Hay dos opciones: 
    A- Por Nomenclatura: en base a cómo armo el nombre, Spring Data JPA arma la lógica internamente. Es decir, utiliza convenciones
    de nomenclatura para generar automáticamente la lógica de los métodos en los repositorios.
    B- Método con @Query: Se usa la anotación @Query para definir manualmente la consulta en JPQL (Java Persistence Query Language).
    JPQL es similar a SQL, pero está orientado a entidades en lugar de tablas directamente. 
    Usar JPQL en lugar de consultas SQL nativas, es mejor, dado que mantiene la independencia de la base de datos.

Nomenclatura:
    1. Nombres de Métodos:
    Encuentra un registro por un campo específico:    findByCampo
    Ejemplo: findByNombreAlumno(String nombre)

    Encuentra todos los registros que cumplan con una condición:    findAllByCampo
    Ejemplo: findAllByApellido(String apellido)

    Ordenar los resultados:     findByCampoOrderByOtroCampoAsc/Desc
    Ejemplo: findByNombreOrderByApellidoAsc()

    Buscar por múltiples campos:    findByCampo1AndCampo2
    Ejemplo: findByNombreAndApellido(String nombre, String apellido)

    2. Operadores:
    And: Para condiciones donde ambos campos deben coincidir.       findByNombreAndApellido(String nombre, String apellido)
    
    Or: Para condiciones donde cualquiera de los campos debe coincidir.     findByNombreOrApellido(String nombre, String apellido)
    
    Is, Equals: Para igualdades.    findByNombreIs(String nombre)
    
    Between: Para rangos de valores.    findByEdadBetween(int startAge, int endAge)
    
    LessThan, GreaterThan, etc.: Para comparaciones.    findByEdadLessThan(int age)
    
    3. Ordenación:
    OrderBy seguido del campo y la dirección (Asc para ascendente, Desc para descendente).      findAllByOrderByApellidoDesc()
    Ejemplo:  Si quiero un método en IProfesorRepository que traiga a los alumnos ordenados por apellido en orden descendente, podría hacerlo así:
    List<Alumno> findAllByOrderByApellidoDesc();
    Spring Data JPA interpretará este nombre y generará automáticamente la consulta SQL correspondiente.

    Resumen: La clave está en nombrar los métodos según estas convenciones, y Spring Data JPA generará las consultas adecuadas
    sin necesidad de escribir código SQL explícito. 


Comparación de las formas posibles:
    Método con convención de nombres: findByNombreAlumno(String nombreAlumno)
    Spring Data JPA genera automáticamente la consulta basándose en el nombre del método.
    
    Método con @Query: buscarPorNombre(@Param("nombre") String nombreAlumno)
    Se usa la anotación @Query para definir manualmente la consulta en JPQL.
    Es más flexible porque se pueden escribir consultas más complejas que no siguen la convención de nombres.
    En este ejemplo, la consulta "SELECT a FROM Alumno a WHERE a.nombreAlumno = :nombre" selecciona a un alumno (Alumno a)
    donde el nombre del alumno (nombreAlumno) coincide con el parámetro :nombre.
    
Conclusión:  Ambos métodos logran lo mismo, pero con enfoques diferentes. El primero es más simple y automático,
    mientras que el segundo da más control y flexibilidad sobre la consulta. Usar @Query es útil cuando se necesita
    hacer consultas más complejas que no pueden ser expresadas simplemente con nombres de métodos.

    *ResponseEntity: forma general para manejar estados, headers y body en todos los casos de la misma forma
    *Como crear un metodo, distinto, a los que ofrece JpaRepository. Y sus dos opciones
    
    Listas ordenadas u otro metodo en particular
    @Query
    muchos a muchos
    transacciones
    swagger
    */
