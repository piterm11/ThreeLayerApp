package com.client;

import com.api.API;
import com.entities.GradeEntity;
import com.entities.StudentEntity;
import com.entities.SubjectEntity;

import com.exceptions.*;
import com.test.APIInterface;
import com.test.Server;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private APIInterface server;
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
        try {
            Registry registry = LocateRegistry.getRegistry("localhost",2022);
            server = (APIInterface) registry.lookup("Server");
            server.initConnection();
            refresh();
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
            TableColumn<GradeEntity, Button> xddd = new TableColumn<>("X");
            xddd.setCellValueFactory(
                    new PropertyValueFactory<>("removeButton"));
            grades.getColumns().addAll(Arrays.asList(subjectGradeName, subjectGrade, xddd));
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

    }

    private void getData() throws RemoteException {
        server.closeConnection();
        server.initConnection();
        studentsData = FXCollections.observableList(server.getStudentsList());
        System.out.println(studentsData);
        subjectsData = FXCollections.observableList(server.getSubjectList());
    }

    private void refresh() throws RemoteException {
        getData();
        students.setItems(studentsData);
        for (var row: studentsData) {
            Button removeButton = new Button("X");
            removeButton.setOnMouseClicked(event -> {
                try {
                    removeStudent(row.getIndex());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
            removeButton.setCursor(Cursor.HAND);
            removeButton.setStyle("-fx-background-color: #c21d1d;" );
            removeButton.setTextFill(Paint.valueOf("WHITE"));
            row.setRemoveButton(removeButton);
        }
        averages.setItems(subjectsData);
        for (var row: subjectsData) {
            Button removeButton = new Button("X");
            removeButton.setOnMouseClicked(event -> {
                try {
                    removeSubject(row.getSubjectName());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
            removeButton.setCursor(Cursor.HAND);
            removeButton.setStyle("-fx-background-color: #c21d1d;" );
            removeButton.setTextFill(Paint.valueOf("WHITE"));
            row.setRemoveButton(removeButton);
        }
        if (gradesData == null) return;
        for (var row: gradesData) {
            Button removeButton = new Button("X");
            removeButton.setOnMouseClicked(event -> {
                try {
                    removeGrade(row.getSubjectName().getValue(), row.getStudent_id());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
            removeButton.setCursor(Cursor.HAND);
            removeButton.setStyle("-fx-background-color: #c21d1d;" );
            removeButton.setTextFill(Paint.valueOf("WHITE"));
            row.setRemoveButton(removeButton);
        }
        grades.setItems(gradesData);
    }
    public void removeStudent(int index) throws RemoteException {
        StudentEntity se = server.findStudent(index);
        server.removeStudent(index);
        refresh();
    }
    public void removeGrade(String subjectName, int index) throws RemoteException {
        server.removeGrade(subjectName, index);
        refresh();
    }
    public void removeSubject(String subjectName) throws RemoteException {
        server.removeSubject(subjectName);
        refresh();
    }
    public void onClick() throws RemoteException {
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
                server.addStudent(
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
                server.addGrade(
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
    public void shutdown() throws RemoteException {
        server.closeConnection();
    }
}
