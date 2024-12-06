import config.Database;
import repositories.GradeRepositoryDbImpl;
import repositories.StudentRepositoryDbImpl;
import repositories.TeacherNoteRepositoryDbImpl;
import services.GradeServiceImpl;
import services.StudentServiceImpl;
import services.TeacherNoteServiceImpl;
import views.MainMenuView;

public class Main {
    public static void main(String[] args) {
        Database database = new Database("school_management", "root", "", "localhost", "3306");
        database.setup();  // Membuka koneksi database

        try {
            // Inisialisasi repository
            var studentRepository = new StudentRepositoryDbImpl(database);
            var gradeRepository = new GradeRepositoryDbImpl(database);
            var teacherNoteRepository = new TeacherNoteRepositoryDbImpl(database);  // Repository untuk catatan guru

            // Inisialisasi service
            var studentService = new StudentServiceImpl(studentRepository);
            var gradeService = new GradeServiceImpl(gradeRepository);
            var teacherNoteService = new TeacherNoteServiceImpl(teacherNoteRepository);  // Service untuk catatan guru

            // Inisialisasi main menu dengan semua service
            var mainMenu = new MainMenuView(studentService, gradeService, teacherNoteService);
            mainMenu.run();
        } finally {
            database.close();  // Tutup koneksi hanya setelah program selesai
        }
    }
}
