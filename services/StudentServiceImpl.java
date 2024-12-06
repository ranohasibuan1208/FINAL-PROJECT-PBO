package services;

import entities.Student;
import repositories.StudentRepository;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void addStudent(Student student) {
        studentRepository.addStudent(student);  // Menambahkan siswa ke database
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.getAllStudents();  // Mendapatkan semua siswa
    }

    @Override
    public boolean updateStudent(Student student) {
        return false;
    }

    @Override
    public boolean deleteStudent(String id) {
        return false;
    }

    @Override
    public Student getStudentById(String studentId) {
        return studentRepository.getStudentById(studentId);  // Mendapatkan siswa berdasarkan ID
    }
}

