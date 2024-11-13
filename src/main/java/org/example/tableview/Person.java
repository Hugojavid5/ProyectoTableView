package org.example.tableview;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Clase que representa a una persona con atributos básicos como nombre, apellido y fecha de nacimiento,
 * así como algunas reglas de negocio específicas del dominio.
 */
public class Person {
    private static AtomicInteger personSequence = new AtomicInteger(1); // Inicia el ID en 1
    private int personId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    // Enumeración para definir las categorías de edad
    public enum AgeCategory {
        BABY, CHILD, TEEN, ADULT, SENIOR, UNKNOWN
    }

    /**
     * Constructor por defecto que inicializa una persona con valores nulos.
     */
    public Person() {
        this(null, null, null);
    }

    /**
     * Constructor que inicializa una persona con los valores especificados y asigna un `personId` único.
     * @param firstName El primer nombre de la persona.
     * @param lastName  El apellido de la persona.
     * @param birthDate La fecha de nacimiento de la persona.
     */
    public Person(String firstName, String lastName, LocalDate birthDate) {
        this.personId = personSequence.getAndIncrement(); // Asigna y luego incrementa el contador
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    // Getters y setters, y otros métodos permanecen igual
    public int getPersonId() {
        return personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public AgeCategory getAgeCategory() {
        if (birthDate == null) {
            return AgeCategory.UNKNOWN;
        }
        long years = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        if (years >= 0 && years < 2) {
            return AgeCategory.BABY;
        } else if (years >= 2 && years < 13) {
            return AgeCategory.CHILD;
        } else if (years >= 13 && years <= 19) {
            return AgeCategory.TEEN;
        } else if (years > 19 && years <= 50) {
            return AgeCategory.ADULT;
        } else if (years > 50) {
            return AgeCategory.SENIOR;
        } else {
            return AgeCategory.UNKNOWN;
        }
    }

    @Override
    public String toString() {
        return "[personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate + "]";
    }
}
