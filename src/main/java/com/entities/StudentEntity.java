package com.entities;

import com.api.API;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "studenci", schema = "baza")
public class StudentEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "indeks")
    private int index;
    @Basic
    @Column(name = "nazwisko")
    private String lastName;
    @Basic
    @Column(name = "imie")
    private String name;

    @OneToMany(mappedBy = "student_id",fetch = FetchType.EAGER)
    private List<GradeEntity> grades;

    public int getId() {
        return id;
    }

    @Transient
    private Button removeButton;
    {
        this.removeButton = new Button("X");
        this.removeButton.setOnMouseClicked(event -> remove());
        this.removeButton.setCursor(Cursor.HAND);
        this.removeButton.setStyle("-fx-background-color: #c21d1d;" );
        this.removeButton.setTextFill(Paint.valueOf("WHITE"));
    }
    public void remove(){
        API api = new API();
        api.initConnection();
        StudentEntity se = api.findStudent(this.index);
        api.removeStudent(se);
        api.closeConnection();
    }
    public Button getRemoveButton() {
        return removeButton;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int indeks) {
        this.index = indeks;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GradeEntity> getGrades() {
        return grades;
    }

    public void setGrades(List<GradeEntity> grades) {
        this.grades = grades;
    }

    public DoubleProperty getAverage(){
        double ret = 0f;
        for(GradeEntity ge : grades){
            ret+=ge.getGrade();
        }
        return new SimpleDoubleProperty(ret / grades.size());
    }

    @Override
    public String toString() {
        return "indeks: " + index + "\nnazwisko: " + lastName +"\nimie: " + name + "\noceny: "+ grades;
    }
    public String toString(boolean onlyPersonalData) {
        return "indeks: " + index + "\nnazwisko: " + lastName +"\nimie: " + name;
    }
}
