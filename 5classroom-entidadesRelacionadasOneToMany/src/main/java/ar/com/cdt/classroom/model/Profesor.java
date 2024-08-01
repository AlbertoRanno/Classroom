package ar.com.cdt.classroom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Entity
@Data
@Table (name="Profesor")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idProfesor")
public class Profesor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_profesor")
    private int idProfesor;
    
    @Column(name="nombre_profesor", length= 45)
    private String nombreProfesor;
    
    @Column (name="apellido_profesor", length= 45)
    private String apellidoProfesor;
    
    @Column (name="telefono_profesor", length= 45)
    private String telefonoProfesor;
    
    @Column (name="asignatura_profesor", length= 45)
    private String asignaturaProfesor;
    
    @Column (name="antiguedad_profesor")
    private int antiguedadProfesor;
    
    @OneToOne(mappedBy = "profesor", optional = true)
    private Alumno alumno;
    
    /*Un profesor, puede dar varios cursos (pero cada curso solo puede ser dado por un profesor) 
    (mappedBy = "profesor"): Indica que la relación está mapeada por el campo profesor en la clase Curso.
    cascade = CascadeType.ALL: Propaga todas las operaciones (persistencia, eliminación, etc.) del profesor a sus cursos.
    orphanRemoval = true: Elimina cursos huérfanos cuando se eliminen de la lista de cursos del profesor.
    @OneToMany: Especifica una relación donde una entidad puede tener muchas entidades asociadas en otra tabla. Se usa típicamente en el lado "uno".*/
    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Curso> cursos;
}