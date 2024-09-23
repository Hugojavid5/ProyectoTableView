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
    private static AtomicInteger personSequence = new AtomicInteger(0);
    private int personId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    /**
     * Enumeración que define las categorías de edad.
     */
    public enum AgeCategory {
        BABY, CHILD, TEEN, ADULT, SENIOR, UNKNOWN
    };

    /**
     * Constructor por defecto que inicializa una persona con valores nulos.
     */
    public Person() {
        this(null, null, null);
    }

    /**
     * Constructor que inicializa una persona con los valores especificados.
     * @param firstName El primer nombre de la persona.
     * @param lastName  El apellido de la persona.
     * @param birthDate La fecha de nacimiento de la persona.
     */
    public Person(String firstName, String lastName, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    /**
     * Obtiene el ID de la persona.
     * @return El ID de la persona.
     */
    public int getPersonId() {
        return personId;
    }

    /**
     * Establece el ID de la persona.
     * @param personId El ID de la persona.
     */
    public void setPersonId(int personId) {
        this.personId = personId;
    }

    /**
     * Obtiene el primer nombre de la persona.
     * @return El primer nombre de la persona.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Establece el primer nombre de la persona.
     * @param firstName El primer nombre de la persona.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Obtiene el apellido de la persona.
     * @return El apellido de la persona.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Establece el apellido de la persona.
     * @param lastName El apellido de la persona.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Obtiene la fecha de nacimiento de la persona.
     * @return La fecha de nacimiento de la persona.
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Establece la fecha de nacimiento de la persona.
     * @param birthDate La fecha de nacimiento de la persona.
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Verifica si la fecha de nacimiento especificada es válida.
     * @param bdate La fecha de nacimiento a verificar.
     * @return true si la fecha de nacimiento es válida, false en caso contrario.
     */
    public boolean isValidBirthDate(LocalDate bdate) {
        return isValidBirthDate(bdate, new ArrayList<>());
    }

    /**
     * Verifica si la fecha de nacimiento especificada es válida, añadiendo cualquier error a la lista de errores proporcionada.
     * @param bdate     La fecha de nacimiento a verificar.
     * @param errorList La lista de errores a la que añadir cualquier error encontrado.
     * @return true si la fecha de nacimiento es válida, false en caso contrario.
     */
    public boolean isValidBirthDate(LocalDate bdate, List<String> errorList) {
        if (bdate == null) {
            return true;
        }
        if (bdate.isAfter(LocalDate.now())) {
            errorList.add("Birth date must not be in future.");
            return false;
        }
        return true;
    }

    /**
     * Verifica si la persona actual es válida.
     * @param errorList La lista de errores a la que añadir cualquier error encontrado.
     * @return true si la persona es válida, false en caso contrario.
     */
    public boolean isValidPerson(List<String> errorList) {
        return isValidPerson(this, errorList);
    }

    /**
     * Verifica si la persona especificada es válida, añadiendo cualquier error a la lista de errores proporcionada.
     * @param p         La persona a verificar.
     * @param errorList La lista de errores a la que añadir cualquier error encontrado.
     * @return true si la persona es válida, false en caso contrario.
     */
    public boolean isValidPerson(Person p, List<String> errorList) {
        boolean isValid = true;
        String fn = p.getFirstName();
        if (fn == null || fn.trim().isEmpty()) {
            errorList.add("First name must contain minimum one character.");
            isValid = false;
        }
        String ln = p.getLastName();
        if (ln == null || ln.trim().length() == 0) {
            errorList.add("Last name must contain minimum one character.");
            isValid = false;
        }
        if (!isValidBirthDate(this.getBirthDate(), errorList)) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * Obtiene la categoría de edad de la persona basada en su fecha de nacimiento.
     * @return La categoría de edad de la persona.
     */
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

    /**
     * Guarda la información de la persona si es válida.
     * @param errorList La lista de errores a la que añadir cualquier error encontrado.
     * @return true si la persona fue guardada correctamente, false en caso contrario.
     */
    public boolean save(List<String> errorList) {
        boolean isSaved = false;
        if (isValidPerson(errorList)) {
            System.out.println("Saved " + this.toString());
            isSaved = true;
        }
        return isSaved;
    }

    @Override
    public String toString() {
        return "[personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate + "]";
    }
}