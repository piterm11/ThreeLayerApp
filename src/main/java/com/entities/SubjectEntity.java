package com.entities;

import com.api.API;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "przedmioty", schema = "baza")
public class SubjectEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nazwa")
    private String subjectName;

    @OneToMany(mappedBy = "subject",fetch = FetchType.EAGER)
    private List<GradeEntity> grades;

    @Transient
    private Button removeButton;

    public void setRemoveButton(Button removeButton) {
        this.removeButton = removeButton;
    }

    public Button getRemoveButton() {
        return removeButton;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<GradeEntity> getGrades() {
        return grades;
    }

    public void setGrades(List<GradeEntity> grade) {
        this.grades = grade;
    }

    public StringProperty getAverage(){
        double ret = 0f;
        for(GradeEntity ge : grades){
            ret+=ge.getGrade();
        }
        return new SimpleStringProperty(String.format("%.2f", ret / grades.size()));
    }
    @Override
    public String toString() {
        return subjectName;
    }
}
