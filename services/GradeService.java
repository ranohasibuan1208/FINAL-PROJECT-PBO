package services;

import entities.Grade;

import java.util.List;

public interface GradeService {
    void addGrade(Grade grade);

    List<Grade> getGradesByStudentId(String studentId);

    double calculateAverageGrade(String studentId);

    List<Grade> getRankedStudents();
}
