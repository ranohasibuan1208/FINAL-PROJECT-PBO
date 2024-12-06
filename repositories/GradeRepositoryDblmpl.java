package repositories;

import config.Database;
import entities.Grade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeRepositoryDbImpl implements GradeRepository {
    private final Database database;

    public GradeRepositoryDbImpl(Database database) {
        this.database = database;
    }

    @Override
    public void addGrade(Grade grade) {
        String sql = "INSERT INTO grades (student_id, subject, value) VALUES (?, ?, ?)";
        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, grade.getStudentId());
            stmt.setString(2, grade.getSubject());
            stmt.setInt(3, grade.getValue());
            stmt.executeUpdate();
            System.out.println("Nilai berhasil ditambahkan untuk siswa ID: " + grade.getStudentId());
        } catch (SQLException e) {
            System.err.println("Gagal menambahkan nilai: " + e.getMessage());
        }
    }

    @Override
    public List<Grade> getGradesByStudentId(String studentId) {
        String sql = "SELECT * FROM grades WHERE student_id = ?";
        List<Grade> grades = new ArrayList<>();
        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Grade grade = new Grade();
                    grade.setStudentId(rs.getString("student_id"));
                    grade.setSubject(rs.getString("subject"));
                    grade.setValue(rs.getInt("value"));
                    grades.add(grade);
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil nilai siswa ID: " + studentId + " - " + e.getMessage());
        }
        return grades;
    }

    @Override
    public double calculateAverageGrade(String studentId) {
        String sql = "SELECT AVG(value) AS average FROM grades WHERE student_id = ?";
        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("average");
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal menghitung rata-rata nilai siswa ID: " + studentId + " - " + e.getMessage());
        }
        return 0; // Jika tidak ada nilai
    }

    @Override
    public List<Grade> getRankedStudents() {
        String sql = """
            SELECT student_id, AVG(value) AS average
            FROM grades
            GROUP BY student_id
            ORDER BY average DESC
        """;
        List<Grade> rankings = new ArrayList<>();
        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Grade grade = new Grade();
                grade.setStudentId(rs.getString("student_id"));
                grade.setValue((int) Math.round(rs.getDouble("average"))); // Nilai rata-rata dibulatkan
                rankings.add(grade);
            }
            System.out.println("Ranking siswa berhasil diambil.");
        } catch (SQLException e) {
            System.err.println("Gagal mengambil ranking siswa: " + e.getMessage());
        }
        return rankings;
    }
}
