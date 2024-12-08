package repositories;

import config.Database;
import entities.TeacherNote;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TeacherNoteRepositoryDbImpl implements TeacherNoteRepository {
    private final Database database;

    public TeacherNoteRepositoryDbImpl(Database database) {
        this.database = database;
    }

    @Override
    public void addNote(TeacherNote note) {
        String sql = "INSERT INTO teacher_notes (student_id, note) VALUES (?, ?)";
        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, note.getStudentId());
            stmt.setString(2, note.getNote());
            stmt.executeUpdate();
            System.out.println("Catatan berhasil ditambahkan ke database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
