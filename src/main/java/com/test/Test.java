package com.test;

import com.api.API;
import com.entities.GradeEntity;
import com.entities.StudentEntity;
import com.entities.SubjectEntity;
import com.exceptions.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class Test {

    API api = new API();
    static Test test = new Test();
    Scanner scanner;

    public static void main(String[] args) throws Exception {
            test.init();
            test.mainLoop();
            test.close();
    }


    void init() {
        api.initConnection();
        scanner = new Scanner(System.in);
    }
    void close() {
        api.closeConnection();
    }
    void mainLoop() throws Exception {
        while(true){
            clearScreen();
            System.out.println("""
                Select operation:
                1. Display Students
                2. Add Student
                3. Update Student
                4. Remove Student
                5. Display Grades
                6. Add Grade
                7. Update Grade
                8. Remove Grade
                9. Display Subjects
                10. Add Subject
                11. Update Subject
                12. Remove Subject
                0. Exit
                """);
            String text = scanner.nextLine();
            try{
                int v = Integer.parseInt(text);
                if(v==0)break;
                operate(v);
            }catch (Exception e){
                throw new Exception(e);
            }
            System.out.println("Press enter to continue...");
            scanner.nextLine();
        }
    }

    void operate(int value) throws Exception {
        switch (value){
            case 1 -> displayStudents();
            case 2 -> addStudent();
            case 3 -> updateStudent();
            case 4 -> removeStudent();
            case 5 -> displayGrades();
            case 6 -> addGrade();
            case 7 -> updateGrade();
            case 8 -> removeGrade();
            case 9 -> displaySubjects();
            case 10 -> addSubject();
            case 11 -> updateSubject();
            case 12 -> removeSubject();
        }
    }

    void displayStudents() throws RemoteException {
        System.out.println("Current list of student:");
        display(api.getStudentsList());
    }

    void displayGrades() throws RemoteException{
        System.out.println("Current list of grades:");
        display(api.getGradeList());
    }

    void displaySubjects() throws RemoteException{
        System.out.println("Current list of subjects:");
        display(api.getSubjectList());
    }

    void display(@NotNull List list){
        for(Object o : list){
            System.out.println(o);
        }
    }

    void addStudent() throws Exception {
        System.out.println("Insert student name:");
        String name = scanner.nextLine();
        System.out.println("Insert student surname:");
        String lastName = scanner.nextLine();
        System.out.println("Insert student index number:");
        String str = scanner.nextLine();
        try{
            int index = Integer.parseInt(str);
            api.addStudent(name,lastName,index);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    StudentEntity findStudent() throws Exception {
        System.out.println("Insert student index number:");
        String str = scanner.nextLine();
        try{
            int index = Integer.parseInt(str);
            StudentEntity se = api.findStudent(index);
            if(se == null)
                throw new StudentNotFoundException("Student with index "+index+" not found!");
            return se;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    void updateStudent() throws Exception {
        try{
            StudentEntity se = findStudent();

            System.out.println("Found student!\n"+se.toString(true));

            System.out.println("Insert student name(leave empty if unchanged):");
            String name = scanner.nextLine();
            if(name.equals(""))
                name=se.getName();
            System.out.println("Insert student surname(leave empty if unchanged):");
            String lastName = scanner.nextLine();
            if(lastName.equals(""))
                lastName=se.getLastName();
            System.out.println("Insert student index number(leave empty if unchanged):");
            String str = scanner.nextLine();
            int index;
            if(str.equals(""))
                index=se.getIndex();
            else
                index = Integer.parseInt(str);
            api.updateStudent(se,name,lastName,index);
        }catch (Exception e){
            throw new Exception(e);
        }

    }

    void removeStudent() throws Exception {
        api.removeStudent(findStudent());
    }

    void addGrade() throws Exception {
        System.out.println("Insert subject name:");
        String subjectName = scanner.nextLine();
        System.out.println("Insert student index number:");
        String str = scanner.nextLine();
        try{
            int index = Integer.parseInt(str);
            System.out.println("Insert grade:");
            str = scanner.nextLine();
            double value = Double.parseDouble(str);
            api.addGrade(subjectName,index,value);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    GradeEntity findGrade() throws Exception {
        System.out.println("Insert subject name:");
        String subjectName = scanner.nextLine();
        System.out.println("Insert student index number:");
        String str = scanner.nextLine();
        try{
            int index = Integer.parseInt(str);
            GradeEntity ge = api.findGrade(subjectName,index);
            if(ge == null)
                throw new GradeNotFoundException("Student with index "+index+" does not have a grade in "+subjectName+"!");
            return ge;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    void updateGrade() throws Exception {
        try{
            GradeEntity ge = findGrade();
            System.out.println("Found grade!\n"+ge);

            System.out.println("Insert new grade:");
            String str = scanner.nextLine();
            double value = Double.parseDouble(str);
            api.updateGrade(ge,value);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    void removeGrade() throws Exception{
        api.removeGrade(findGrade());
    }

    void addSubject() throws Exception {
        System.out.println("Insert subject name:");
        String subjectName = scanner.nextLine();
        try{
            api.addSubject(subjectName);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    SubjectEntity findSubject() throws Exception {
        System.out.println("Insert subject name:");
        String subjectName = scanner.nextLine();
        try{
            SubjectEntity se = api.findSubject(subjectName);
            if(se==null)
                throw new SubjectNotFoundException("Subject "+subjectName+" not found!");
            return se;
        }catch(Exception e){
            throw new Exception(e);
        }
    }

    void updateSubject() throws Exception {
        try{
            SubjectEntity se = findSubject();
            System.out.println("Found subject!\n"+se);
            System.out.println("Insert new name:");
            String subjectName = scanner.nextLine();
            api.updateSubject(se,subjectName);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    void removeSubject() throws Exception {
        api.removeSubject(findSubject());
    }
    private static void clearScreen() throws Exception {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException e) {
            throw  new Exception(e);
        }
    }
}
