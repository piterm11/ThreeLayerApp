package com.entities;

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

    public double getAverage(){
        double ret = 0f;
        for(GradeEntity ge : grades){
            ret+=ge.getGrade();
        }
        return ret/ grades.size();
    }

    @Override
    public String toString() {
        return "indeks: " + index + "\nnazwisko: " + lastName +"\nimie: " + name + "\noceny: "+ grades;
    }
    public String toString(boolean onlyPersonalData) {
        return "indeks: " + index + "\nnazwisko: " + lastName +"\nimie: " + name;
    }
}
