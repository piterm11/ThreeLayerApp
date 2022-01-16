package com.test;

import com.api.API;
import com.entities.GradeEntity;
import com.entities.StudentEntity;
import com.entities.SubjectEntity;
import com.exceptions.*;
import org.jetbrains.annotations.NotNull;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Server extends UnicastRemoteObject implements APIInterface {

    public Server() throws RemoteException {
        super();
    }
    API api = new API();
    public static void main(String[] args) {
        try {
            Server s = new Server();

            Registry registry = LocateRegistry.createRegistry(2022);
            registry.bind("Server",s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initConnection() throws RemoteException {
        api.initConnection();
    }

    @Override
    public void closeConnection() throws RemoteException {
        api.closeConnection();
    }

    @Override
    public void addStudent(String name, String lastName, int index) throws StudentExistsException, RemoteException {
        api.addStudent(name, lastName, index);
    }

    @Override
    public void updateStudent(int index, String name, String lastName, int newIndex) throws RemoteException {
        StudentEntity se = api.findStudent(index);
        api.updateStudent(se,name,lastName,newIndex);
    }

    @Override
    public void removeStudent(int index) throws RemoteException {
        StudentEntity se= api.findStudent(index);
        api.removeStudent(se);
    }

    @Override
    public StudentEntity findStudent(int index) throws RemoteException {
        return api.findStudent(index);
    }

    @Override
    public List<StudentEntity> getStudentsList() throws RemoteException {
        return api.getStudentsList();
    }

    @Override
    public void addSubject(String subjectName) throws SubjectExistsException, RemoteException {
        api.addSubject(subjectName);
    }

    @Override
    public void updateSubject(String subjectName, String newName) throws RemoteException {
        SubjectEntity se = api.findSubject(subjectName);
        api.updateSubject(se,newName);
    }

    @Override
    public void removeSubject(String subjectName) throws RemoteException {
        SubjectEntity se = api.findSubject(subjectName);
        api.removeSubject(se);
    }

    @Override
    public SubjectEntity findSubject(String subjectName) throws RemoteException {
        return api.findSubject(subjectName);
    }

    @Override
    public List<SubjectEntity> getSubjectList() throws RemoteException {
        return api.getSubjectList();
    }

    @Override
    public void addGrade(String subjectName, int studentIndex, double value) throws SubjectNotFoundException, StudentNotFoundException, GradeExistsException, GradeOutOfRangeException, RemoteException {
        api.addGrade(subjectName,studentIndex,value);
    }

    @Override
    public void updateGrade(String subjectName, int studentIndex, double value) throws RemoteException {
        GradeEntity ge = api.findGrade(subjectName,studentIndex);
        api.updateGrade(ge,value);
    }

    @Override
    public void removeGrade(String subjectName, int studentIndex) throws RemoteException {
        GradeEntity ge = api.findGrade(subjectName,studentIndex);
        api.removeGrade(ge);
    }

    @Override
    public GradeEntity findGrade(String subjectName, int studentIndex) throws RemoteException {
        return api.findGrade(subjectName,studentIndex);
    }

    @Override
    public List<GradeEntity> getGradeList() throws RemoteException {
        return api.getGradeList();
    }
}
