package services;

import entities.Student;

import java.util.List;

public interface StudentService {
    void addStudent(Student student);

    Student getStudentById(String id);

    List<Student> getAllStudents();

    boolean updateStudent(Student student);

    boolean deleteStudent(String id);
}
