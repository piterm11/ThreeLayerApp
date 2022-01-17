package com.client;

import com.api.API;
import com.entities.GradeEntity;
import com.entities.StudentEntity;
import com.entities.SubjectEntity;

import com.exceptions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final API api = new API();
    @FXML
    private TableView<StudentEntity> students;
    @FXML
    private TableView<SubjectEntity> averages;
    @FXML
    private TableView<GradeEntity> grades;

    private ObservableList<GradeEntity> gradesData;
    private ObservableList<StudentEntity> studentsData;
    private ObservableList<SubjectEntity> subjectsData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        api.initConnection();
        getData();

        TableColumn<StudentEntity, Integer> index = new TableColumn<>("Index");
        index.setCellValueFactory(
                new PropertyValueFactory<>("index"));

        TableColumn<StudentEntity, String> firstName = new TableColumn<>("First Name");
        firstName.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        TableColumn<StudentEntity, String> lastName = new TableColumn<>("Last Name");
        lastName.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));
        TableColumn<StudentEntity, Button> xd = new TableColumn<>(" ");
        xd.setCellValueFactory(
                new PropertyValueFactory<>("removeButton"));
        students.getColumns().addAll(Arrays.asList(index, firstName, lastName, xd));

        TableColumn<SubjectEntity, String> subjectName = new TableColumn<>("Subject Name");
        subjectName.setCellValueFactory(
                new PropertyValueFactory<>("subjectName"));

        TableColumn<SubjectEntity, String> subjectAverage = new TableColumn<>("Average of Grades");
        subjectAverage.setCellValueFactory(
                p -> p.getValue().getAverage()
        );
        TableColumn<SubjectEntity, Button> xdd = new TableColumn<>(" ");
        xdd.setCellValueFactory(
                new PropertyValueFactory<>("removeButton"));
        averages.getColumns().addAll(Arrays.asList(subjectName, subjectAverage, xdd));

        TableColumn<GradeEntity, String> subjectGradeName = new TableColumn<>("Subject Name");
        subjectGradeName.setCellValueFactory(
                p -> p.getValue().getSubjectName()
        );

        TableColumn<GradeEntity, Double> subjectGrade = new TableColumn<>("Grade");
        subjectGrade.setCellValueFactory(
                new PropertyValueFactory<>("grade")
        );
        TableColumn<GradeEntity, Button> xddd = new TableColumn<>(" ");
        xddd.setCellValueFactory(
                new PropertyValueFactory<>("removeButton"));
        grades.getColumns().addAll(Arrays.asList(subjectGradeName, subjectGrade, xddd));
        refresh();
    }

    private void getData() {
        studentsData = FXCollections.observableList(api.getStudentsList());
        subjectsData = FXCollections.observableList(api.getSubjectList());
    }

    private void refresh() {
        getData();
        students.setItems(studentsData);
        averages.setItems(subjectsData);
        grades.setItems(gradesData);
    }

    public void onClick(){
        if (students.getSelectionModel().getSelectedItem() == null) return;
        gradesData = FXCollections.observableList(students.getSelectionModel().getSelectedItem().getGrades());
        grades.setItems(gradesData);
    }
    public void onAddStudent() throws IOException {
        Dialog<ButtonType> dialog = new Dialog<>();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("dialog_student.fxml"));
        DialogPane scene = fxmlLoader.load();
        dialog.setDialogPane(scene);
        dialog.setTitle("Add student");

        Optional<ButtonType> choice = dialog.showAndWait();
        if (choice.isPresent() && choice.get() == ButtonType.OK){
            TextField firstName = (TextField) scene.lookup("#firstName");
            TextField lastName = (TextField) scene.lookup("#lastName");
            TextField index = (TextField) scene.lookup("#index");
            System.out.println(firstName.getText());
            System.out.println(lastName.getText());
            System.out.println(index.getText());
            try {
                api.addStudent(
                        firstName.getText(),
                        lastName.getText(),
                        Integer.parseInt(index.getText())
                );
            } catch (StudentExistsException e) {
                e.printStackTrace();
            }
            refresh();
        }
    }
    public void onAddGrade() throws IOException {
        System.out.println("XD");
        Dialog<ButtonType> dialog = new Dialog<>();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("dialog_grade.fxml"));
        DialogPane scene = fxmlLoader.load();
        dialog.setDialogPane(scene);
        dialog.setTitle("Add grade");
        Optional<ButtonType> choice = dialog.showAndWait();
        if (choice.isPresent() && choice.get() == ButtonType.OK){
            int index = students.getSelectionModel().getSelectedItem().getIndex();
            TextField subjectName = (TextField) scene.lookup("#subjectName");
            TextField grade = (TextField) scene.lookup("#grade");
            System.out.println(subjectName.getText());
            System.out.println(grade.getText());
            try {
                api.addGrade(
                        subjectName.getText(),
                        index,
                        Double.parseDouble(grade.getText())
                );
            } catch (SubjectNotFoundException | StudentNotFoundException | GradeExistsException | GradeOutOfRangeException e) {
                e.printStackTrace();
            }
            refresh();
            onClick();
        }
    }
    public void shutdown(){
        api.closeConnection();
    }
}
