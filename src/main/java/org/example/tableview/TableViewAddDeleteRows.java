package org.example.tableview;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Arrays;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import static javafx.scene.control.TableView.TableViewSelectionModel;
import java.time.LocalDate;

/**
 * Clase que permite agregar y eliminar filas en un TableView de objetos Person usando JavaFX.
 */
public class TableViewAddDeleteRows extends Application {
    // Campos para añadir detalles de una Persona
    private TextField fNameField;
    private TextField lNameField;
    private DatePicker dobField;
    private TableView<Person> table;

    /**
     * Método principal que lanza la aplicación JavaFX.
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * Método iniciado cuando la aplicación JavaFX empieza.
     * Inicializa y configura los elementos de la interfaz de usuario y muestra la ventana principal.
     * @param stage El escenario principal de la aplicación.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) {
        fNameField = new TextField();
        lNameField = new TextField();
        dobField = new DatePicker();
        table = new TableView<>(PersonTableUtil.getPersonList());

        // Activa la selección múltiple de filas para el TableView
        TableViewSelectionModel<Person> tsm = table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.MULTIPLE);

        // Añadir columnas al TableView
        table.getColumns().addAll(PersonTableUtil.getIdColumn(), PersonTableUtil.getFirstNameColumn(), PersonTableUtil.getLastNameColumn(), PersonTableUtil.getBirthDateColumn());

        GridPane newDataPane  = this.getNewPersonDataPane();
        Button restoreBtn = new Button("Restore Rows");
        restoreBtn.setOnAction(e -> restoreRows());
        Button deleteBtn = new Button("Delete Selected Rows");
        deleteBtn.setOnAction(e -> deleteSelectedRows());

        VBox root = new VBox(newDataPane, new HBox(restoreBtn, deleteBtn), table);
        root.setSpacing(5);
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinWidth(500);
        stage.setMinHeight(350);
        stage.setResizable(false);
        stage.setTitle("Adding/Deleting Rows in a TableViews");
        stage.show();
    }

    /**
     * Crea un panel de entrada de datos para una nueva persona
     * @return Un GridPane que contiene los campos de entrada para una persona.
     */
    public GridPane getNewPersonDataPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(5);
        pane.addRow(0, new Label("Nombre:"), fNameField);
        pane.addRow(1, new Label("Apellido:"), lNameField);
        pane.addRow(2, new Label("Fecha Nacimiento:"), dobField);

        Button addBtn = new Button("Añadir");
        addBtn.setOnAction(e -> addPerson());

        // Añade el botón "Añadir"
        pane.add(addBtn, 2, 0);
        return pane;
    }

    /**
     * Elimina las filas seleccionadas en el TableView.
     */
    public void deleteSelectedRows() {
        TableViewSelectionModel<Person> tsm = table.getSelectionModel();
        if (tsm.isEmpty()) {
            System.out.println("Please select a row to delete.");
            return;
        }

        // Obtiene todos los índices de filas seleccionadas en un array
        ObservableList<Integer> list = tsm.getSelectedIndices();
        Integer[] selectedIndices = new Integer[list.size()];
        selectedIndices = list.toArray(selectedIndices);

        // Ordena el array
        Arrays.sort(selectedIndices);

        // Elimina filas (de la última a la primera)
        for(int i = selectedIndices.length - 1; i >= 0; i--) {
            tsm.clearSelection(selectedIndices[i]);
            table.getItems().remove(selectedIndices[i].intValue());
        }
    }

    /**
     * Restaura las filas del TableView a su estado inicial.
     */
    public void restoreRows() {
        table.getItems().clear();
        table.getItems().addAll(PersonTableUtil.getPersonList());
    }

    /**
     * Añade una nueva persona al TableView.
     */
    public void addPerson() {
        String firstName = fNameField.getText();
        String lastName = lNameField.getText();
        LocalDate dob = dobField.getValue();

        if (firstName == null || firstName.trim().isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }

        if (lastName == null || lastName.trim().isEmpty()) {
            System.out.println("El apellido no puede estar vacío.");
            return;
        }

        if (dob == null) {
            System.out.println("La fecha de nacimiento no puede estar vacía.");
            return;
        }

        Person p = new Person(firstName, lastName, dob);
        table.getItems().add(p);
        clearFields();
    }

    /**
     * Limpia los campos de entrada de datos.
     */
    public void clearFields() {
        fNameField.setText(null);
        lNameField.setText(null);
        dobField.setValue(null);
    }
}