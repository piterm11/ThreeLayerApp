package com.test;

import com.api.API;
import com.entities.GradeEntity;
import com.entities.StudentEntity;
import com.entities.SubjectEntity;
import com.exceptions.*;
import org.jetbrains.annotations.NotNull;

import javax.naming.Context;
import javax.naming.InitialContext;
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
    public void updateStudent(@NotNull StudentEntity s, String name, String lastName, int index) throws RemoteException {
        api.updateStudent(s,name,lastName,index);
    }

    @Override
    public void removeStudent(StudentEntity s) throws RemoteException {
        api.removeStudent(s);
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
    public void updateSubject(@NotNull SubjectEntity p, String newName) throws RemoteException {
        api.updateSubject(p,newName);
    }

    @Override
    public void removeSubject(SubjectEntity p) throws RemoteException {
        api.removeSubject(p);
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
    public void updateGrade(@NotNull GradeEntity o, double value) throws RemoteException {
        api.updateGrade(o,value);
    }

    @Override
    public void removeGrade(GradeEntity o) throws RemoteException {
        api.removeGrade(o);
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
