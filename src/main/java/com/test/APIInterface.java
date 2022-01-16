package com.test;

import com.entities.GradeEntity;
import com.entities.StudentEntity;
import com.entities.SubjectEntity;
import com.exceptions.*;
import org.jetbrains.annotations.NotNull;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface APIInterface extends Remote {
    public void initConnection() throws RemoteException;
    public void closeConnection() throws RemoteException;
    public void addStudent(String name, String lastName, int index) throws StudentExistsException,RemoteException;
    public void updateStudent(int index,String name, String lastName, int newIndex) throws RemoteException;
    public void removeStudent(int index) throws RemoteException;
    public StudentEntity findStudent(int index) throws RemoteException;
    public List<StudentEntity> getStudentsList() throws RemoteException;
    public void addSubject(String subjectName) throws SubjectExistsException,RemoteException;
    public void updateSubject(String subjectName, String newName) throws RemoteException;
    public void removeSubject(String subjectName) throws RemoteException;
    public SubjectEntity findSubject(String subjectName) throws RemoteException;
    public List<SubjectEntity> getSubjectList() throws RemoteException;
    public void addGrade(String subjectName, int studentIndex, double value) throws SubjectNotFoundException, StudentNotFoundException, GradeExistsException, GradeOutOfRangeException,RemoteException;
    public void updateGrade(String subjectName, int studentIndex, double value) throws RemoteException;
    public void removeGrade(String subjectName, int studentIndex) throws RemoteException;
    public GradeEntity findGrade(String subjectName, int studentIndex) throws RemoteException;
    public List<GradeEntity> getGradeList() throws RemoteException;
}
