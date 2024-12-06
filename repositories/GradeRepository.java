package repositories;

import entities.Grade;

import java.util.List;

public interface GradeRepository {
    void addGrade(Grade grade);

    List<Grade> getGradesByStudentId(String studentId);

    double calculateAverageGrade(String studentId);

    List<Grade> getRankedStudents();
}
