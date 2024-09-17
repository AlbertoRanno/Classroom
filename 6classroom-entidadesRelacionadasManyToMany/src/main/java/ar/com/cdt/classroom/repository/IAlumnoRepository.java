package ar.com.cdt.classroom.repository;

import ar.com.cdt.classroom.model.Alumno;
import ar.com.cdt.classroom.model.Profesor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/* Cuando hay métodos que involucran más de una entidad (como agregar un curso a un alumno), es importante organizar el código de manera que siga
    principios de buena arquitectura:
    *    Repositorio: Los métodos en los repositorios (interfaces que extienden JpaRepository) se encargan de operaciones básicas de persistencia (CRUD) 
    y consultas personalizadas. No es usual colocar lógica compleja o cruzada aquí.
    *    Servicio (Implementación del Servicio): Es el lugar adecuado para colocar la lógica que involucra más de una entidad o repositorio.
    En el caso del ejemplo de agregar un curso a un alumno, este método debería ir en la implementación del servicio de Alumno o Curso, o en ambos.
    Servicio de Alumno: Aquí podría tener métodos como agregarCursoAAlumno.
    Servicio de Curso: Aquí podría tener un método como agregarAlumnoACurso.
    *    Servicio (Interfaz): Si el método es general, importante, lo sumo a la interfaz para asegurarme de que todas las implementaciones cuenten con él.
    Pero si es muy específico o sólo se usa en un contexto particular, puedo optar por dejarlo únicamente en la implementación del servicio.
    *    Controlador: Aquí defino los endpoints que llaman a los métodos del servicio. Es donde se maneja la interacción con el cliente (p.ej., Postman). */

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
    Alumno buscarAlumnoPorNombre(@Param("nombre") String nombreAlumno);
    /* Qué hacen las anotaciones @Query y @Param:
    La anotación @Query se usa para definir una consulta personalizada en JPQL (Java Persistence Query Language). JPQL es un lenguaje de consulta orientado a entidades en Java,
    similar a SQL, pero diseñado para trabajar con las entidades de la aplicación en lugar de directamente con las tablas de la base de datos.
    "SELECT a FROM Alumno a WHERE a.nombreAlumno = " Esta es la consulta que se ejecuta.
    SELECT a FROM Alumno a: Esto significa que estamos seleccionando (SELECT) la entidad Alumno (representada por a) desde la tabla asociada a esta entidad.
    WHERE a.nombreAlumno = :nombre: Aquí se especifica una condición (WHERE) para filtrar los resultados. Solo se seleccionarán aquellos alumnos cuyo nombreAlumno coincida 
    con el valor del parámetro :nombre.

    La anotación @Param se usa para vincular un parámetro del método a un parámetro en la consulta JPQL definida en @Query.
    @Param("nombre"): Mapea el parámetro del método nombreAlumno al parámetro :nombre en la consulta JPQL.
    String nombreAlumno: Es el parámetro que se pasa al método desde el código que lo llama.
    
    Funcionamiento:
    Cuando llamo al método buscarAlumnoPorNombre("Juan"), el valor "Juan" se pasa como nombreAlumno.
    @Param("nombre") vincula este valor al parámetro :nombre en la consulta JPQL, que se traduce a WHERE a.nombreAlumno = 'Juan'.    */
    
    //Creado por nomenclatura, para corregir el metodo de eliminar profesor. OJO que (Profesor profesor) debe coincidir con el modelo.. ACORDATE!
    public Alumno findByProfesor(Profesor profesor);
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
    */