package ar.com.cdt.classroom.exceptions;

// Definición de la clase
public class ResourceNotFoundException extends RuntimeException {

    // Constructor de la clase que recibe un mensaje de error
    public ResourceNotFoundException(String mensaje) {
        // Llama al constructor de la clase padre (RuntimeException) con el mensaje de error
        super(mensaje);
    }
}

/*
-Las excepciones, son errores que interrumpen el flujo normal de la app
-Hay de 2 tipos: 
*Checked (Las más graves, por ej IOException)
*Unchecked (Menores, como NullPointerException)
A los efectos prácticos, entiendo que este tipo de excepciones las arrojará el IDE cuando algo está mal,
tanto en tiempos de compilación, como de ejecución, por lo que me limitaré de momento a leerlas, para intentar ubicar y solucionar el error.

-Lo que me parece más interesante, es que puedo definir yo, QuÉ es un error en mi app, y lanzar a raíz de esto un mensaje de mi elección. 
Y para hacer esto, es más flexible, usar las de tipo unchecked, que son las que extienden de RuntimeException, 
dado que son las que No me obligan a manejar la excepci{on, en TODOS los lugares donde podria ocurrir.
Las del otro tipo, checked, que extienden de Exception, si me obligan, y son para manejos de errores de mayor embergadura,
que entiendo pueden o no ser recurrentes, pero no se van a dar en apps iniciales.

Por eso creé esta clase propia, en el paquete, exceptions

Esta clase la voy a usar, cuando en los metodos tengo que manejar la posibilidad de que no se encuentren los recursos.
Por ejemplo, si se quiere modificar o eliminar un alumno, pero en la base de datos no hay ningún registro con ese ID

public boolean modificarAlumno(Integer id, Alumno alumno) {
    if (repo.existsById(id)) {
        alumno.setIdAlumno(id);
        repo.save(alumno);
        return true;
    } else {
        throw new ResourceNotFoundException("Alumno no encontrado");
    }

Esta clase ResourceNotFoundException es una excepción personalizada.
Extiende de RuntimeException, lo que significa que es una excepción no verificada (unchecked),
y no es obligatorio manejarla con try-catch.
Tiene un constructor que acepta un mensaje como parámetro, el cual representa la descripción del error.
El mensaje se pasa a la clase padre RuntimeException utilizando super(mensaje), 
lo que permite que este mensaje sea accesible cuando la excepción es lanzada.
*/

