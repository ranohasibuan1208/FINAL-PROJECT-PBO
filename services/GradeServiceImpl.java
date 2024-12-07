package services;

import entities.Grade;
import repositories.GradeRepository;

import java.util.List;

public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;

    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public void addGrade(Grade grade) {
        gradeRepository.addGrade(grade);  // Menambahkan nilai ke database
    }

    @Override
    public double calculateAverageGrade(String studentId) {
        return gradeRepository.calculateAverageGrade(studentId);  // Menghitung rata-rata nilai
    }

    @Override
    public List<Grade> getRankedStudents() {
        return gradeRepository.getRankedStudents(); // Memanggil metode di repository
    }

    @Override
    public List<Grade> getGradesByStudentId(String studentId) {
        return gradeRepository.getGradesByStudentId(studentId);  // Mendapatkan nilai siswa
    }
}
