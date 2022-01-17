package com.entities;

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

    public double getAverage(){
        double ret = 0f;
        for(GradeEntity ge : grades){
            ret+=ge.getGrade();
        }
        return ret/ grades.size();
    }
    @Override
    public String toString() {
        return subjectName;
    }
}
