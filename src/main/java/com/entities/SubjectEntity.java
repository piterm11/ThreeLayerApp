package com.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "przedmioty", schema = "baza")
public class SubjectEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nazwa")
    private String nazwa;

    @OneToMany(mappedBy = "przedmiot")
    private List<GradeEntity> oceny;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public List<GradeEntity> getOceny() {
        return oceny;
    }

    public void setOceny(List<GradeEntity> oceny) {
        this.oceny = oceny;
    }

    @Override
    public String toString() {
        return nazwa;
    }
}
