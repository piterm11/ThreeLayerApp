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
    private SubjectEntity przedmiot;

    @Basic
    @Column(name = "ocena")
    private double ocena;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int indeks) {
        this.student_id = indeks;
    }



    public SubjectEntity getPrzedmiot() {
        return przedmiot;
    }

    public void setPrzedmiot(SubjectEntity przedmiot) {
        this.przedmiot = przedmiot;
    }


    public double getOcena() {
        return ocena;
    }

    public void setOcena(double ocena) {
        this.ocena = ocena;
    }


    @Override
    public String toString() {
        return przedmiot + ": " + ocena;
    }
}
