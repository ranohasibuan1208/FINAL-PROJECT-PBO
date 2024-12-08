package entities;

public class TeacherNote {
    private int id;
    private String studentId;
    private String note;

    public TeacherNote() {}

    public TeacherNote(String studentId, String note) {
        this.studentId = studentId;
        this.note = note;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
