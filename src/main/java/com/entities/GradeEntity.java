package com.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "oceny", schema = "baza")
public class GradeEntity implements Serializable {
    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "student_id")
    private int student_id;

    @ManyToOne
    @JoinColumn(name = "przedmiot",referencedColumnName = "id")
    private SubjectEntity subject;

    @Basic
    @Column(name = "ocena")
    private double grade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int index) {
        this.student_id = index;
    }



    public SubjectEntity getSubject() {
        return subject;
    }

    public void setSubject(SubjectEntity przedmiot) {
        this.subject = przedmiot;
    }


    public double getGrade() {
        return grade;
    }

    public void setGrade(double ocena) {
        this.grade = ocena;
    }


    @Override
    public String toString() {
        return subject + ": " + grade;
    }
}
