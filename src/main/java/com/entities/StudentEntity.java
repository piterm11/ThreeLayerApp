package com.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "studenci", schema = "baza")
public class StudentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "indeks")
    private int indeks;
    @Basic
    @Column(name = "nazwisko")
    private String nazwisko;
    @Basic
    @Column(name = "imie")
    private String imie;

    @OneToMany(mappedBy = "student_id")
    private List<GradeEntity> oceny;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndeks() {
        return indeks;
    }

    public void setIndeks(int indeks) {
        this.indeks = indeks;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public List<GradeEntity> getOceny() {
        return oceny;
    }

    public void setOceny(List<GradeEntity> oceny) {
        this.oceny = oceny;
    }

    @Override
    public String toString() {
        return "indeks: " + indeks + "\nnazwisko: " + nazwisko +"\nimie: " + imie + "\noceny: "+ oceny;
    }
    public String toString(boolean onlyPersonalData) {
        return "indeks: " + indeks + "\nnazwisko: " + nazwisko +"\nimie: " + imie;
    }
}
