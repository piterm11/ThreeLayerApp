package com.api;

import com.entities.GradeEntity;
import com.entities.SubjectEntity;
import com.entities.StudentEntity;
import com.exceptions.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class API implements Serializable {
    private EntityManagerFactory emf;
    private EntityManager em;
    private final Set<Double> allowedGrades = Set.of(2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0);
    public void initConnection(){
        emf = Persistence.createEntityManagerFactory("default");
        em= emf.createEntityManager();
    }
    public void closeConnection(){
        emf.close();
        em.close();
    }
    public void addStudent(String name, String lastName, int index) throws StudentExistsException {
        StudentEntity s = findStudent(index);
        if(s!=null)
            throw new StudentExistsException("Student with given index "+index+" already exists!");
        em.getTransaction().begin();
        s = new StudentEntity();
        s.setLastName(lastName);
        s.setName(name);
        s.setIndex(index);
        em.persist(s);
        em.getTransaction().commit();
    }
    public void updateStudent(@NotNull StudentEntity s, String name, String lastName, int index){
        em.getTransaction().begin();
        s.setName(name);
        s.setLastName(lastName);
        s.setIndex(index);
        em.getTransaction().commit();
    }
    public void removeStudent(StudentEntity s){
        em.getTransaction().begin();
        for (GradeEntity o : s.getGrades()) {
            em.remove(o);
        }
        em.remove(s);
        em.getTransaction().commit();
    }

    public StudentEntity findStudent(int index){
        List<StudentEntity> list = getStudentsList();
        for(StudentEntity s : list){
            if(s.getIndex()==index){
                return s;
            }
        }
        return null;
    }

    public List<StudentEntity> getStudentsList(){
        return em.createQuery("select s from StudentEntity s").getResultList();
    }

    public void addSubject(String subjectName) throws SubjectExistsException {
        SubjectEntity p = findSubject(subjectName);
        if(p!=null)
            throw new SubjectExistsException("Subject "+subjectName+" already exists!");
        em.getTransaction().begin();
        p = new SubjectEntity();
        p.setSubjectName(subjectName);
        em.persist(p);
        em.getTransaction().commit();
    }
    public void updateSubject(@NotNull SubjectEntity p, String newName){
        em.getTransaction().begin();
        p.setSubjectName(newName);
        em.getTransaction().commit();
    }
    public void removeSubject(SubjectEntity p){
        em.getTransaction().begin();
        for (GradeEntity o : p.getGrades()) {
            em.remove(o);
        }
        em.remove(p);
        em.getTransaction().commit();
    }
    public SubjectEntity findSubject(String subjectName){
        List<SubjectEntity> list = getSubjectList();
        for(SubjectEntity p : list){
            if(p.getSubjectName().equals(subjectName)){
                return p;
            }
        }
        return null;
    }
    public List<SubjectEntity> getSubjectList(){
        return em.createQuery("select p from SubjectEntity p").getResultList();
    }

    public void addGrade(String subjectName, int studentIndex, double value) throws SubjectNotFoundException, StudentNotFoundException, GradeExistsException, GradeOutOfRangeException {
        SubjectEntity pe= findSubject(subjectName);
        StudentEntity se = findStudent(studentIndex);
        if(!allowedGrades.contains(value))
            throw new GradeOutOfRangeException("Grade is out of range!");
        if(pe==null) {
            throw new SubjectNotFoundException("Subject "+subjectName+" not found!");
        }
        if(se==null){
            throw new StudentNotFoundException("Student with index "+studentIndex+" not found!");
        }
        GradeEntity o = findGrade(subjectName,studentIndex);
        if(o!=null)
            throw new GradeExistsException("Student with index "+studentIndex+" already has grade in "+subjectName+"!");
        em.getTransaction().begin();
        GradeEntity g = new GradeEntity();
        g.setSubject(pe);
        g.setGrade(value);
        g.setStudent_id(se.getId());
        em.persist(g);
        em.getTransaction().commit();
    }

    public void updateGrade(@NotNull GradeEntity o, double value){
        em.getTransaction().begin();
        o.setGrade(value);
        em.getTransaction().commit();
    }

    public void removeGrade(GradeEntity o){
        em.getTransaction().begin();
        em.remove(o);
        em.getTransaction().commit();
    }

    public GradeEntity findGrade(String subjectName, int studentIndex) {
        SubjectEntity pe = findSubject(subjectName);
        StudentEntity se = findStudent(studentIndex);
        if(pe==null|| se==null) {
            return null;
        }
        List<GradeEntity> list = getGradeList();
        for(GradeEntity o : list){
            if(o.getSubject()==pe && o.getStudent_id()==se.getId()){
                return o;
            }
        }
        return null;
    }
    public List<GradeEntity> getGradeList(){
        return em.createQuery("select o from GradeEntity o").getResultList();
    }
}
