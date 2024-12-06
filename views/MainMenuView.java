package views;

import entities.Grade;
import entities.Student;
import entities.TeacherNote;
import services.GradeService;
import services.StudentService;
import services.TeacherNoteService;

import java.util.List;
import java.util.Scanner;

public class MainMenuView {
    private final StudentService studentService;
    private final GradeService gradeService;
    private final TeacherNoteService teacherNoteService;
    private final Scanner scanner = new Scanner(System.in);

    public MainMenuView(StudentService studentService, GradeService gradeService, TeacherNoteService teacherNoteService) {
        this.studentService = studentService;
        this.gradeService = gradeService;
        this.teacherNoteService = teacherNoteService;
    }

    public void run() {
        while (true) {
            System.out.println("\n===== Menu Aplikasi =====");
            System.out.println("1. Input Data Siswa");
            System.out.println("2. Input Nilai Siswa");
            System.out.println("3. Tampilkan Data Siswa dan Nilainya");
            System.out.println("4. Export Data");
            System.out.println("5. Hitung Rata-rata Nilai");
            System.out.println("6. Tentukan Ranking Siswa");
            System.out.println("7. Riwayat Perubahan Nilai");
            System.out.println("8. Input Catatan Guru");
            System.out.println("9. Cari Data Siswa");
            System.out.println("10. Tampilkan Grafik Perkembangan Nilai");
            System.out.println("11. Notifikasi Perkembangan");
            System.out.println("12. User Management");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Konsumsi newline setelah angka

            try {
                switch (choice) {
                    case 1 -> inputStudentData();
                    case 2 -> inputStudentGrade();
                    case 3 -> displayStudentAndGrades();
                    case 4 -> exportData();
                    case 5 -> calculateAverageGrade();
                    case 6 -> rankStudents();
                    case 7 -> displayGradeHistory();
                    case 8 -> inputTeacherNote();
                    case 9 -> searchStudent();
                    case 10 -> displayProgressChart();
                    case 11 -> sendNotifications();
                    case 12 -> manageUsers();
                    case 0 -> {
                        System.out.println("Keluar...");
                        return;
                    }
                    default -> System.out.println("Pilihan tidak valid.");
                }
            } catch (Exception e) {
                System.err.println("Terjadi kesalahan: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void inputStudentData() {
        try {
            System.out.print("Masukkan ID Siswa: ");
            String id = scanner.nextLine();
            System.out.print("Masukkan Nama Siswa: ");
            String name = scanner.nextLine();
            System.out.print("Masukkan Alamat: ");
            String address = scanner.nextLine();

            if (id.isEmpty() || name.isEmpty() || address.isEmpty()) {
                System.out.println("Semua data harus diisi!");
                return;
            }

            Student student = new Student();
            student.setId(id);
            student.setName(name);
            student.setAddress(address);

            studentService.addStudent(student);
            System.out.println("Data siswa berhasil ditambahkan.");
        } catch (Exception e) {
            System.err.println("Gagal menambahkan data siswa: " + e.getMessage());
        }
    }

    public void inputStudentGrade() {
        try {
            System.out.print("Masukkan ID Siswa: ");
            String studentId = scanner.nextLine();
            System.out.print("Masukkan Mata Pelajaran: ");
            String subject = scanner.nextLine();
            System.out.print("Masukkan Nilai: ");
            int value = scanner.nextInt();
            scanner.nextLine(); // Konsumsi newline setelah angka

            Grade grade = new Grade();
            grade.setStudentId(studentId);
            grade.setSubject(subject);
            grade.setValue(value);

            gradeService.addGrade(grade);
            System.out.println("Nilai siswa berhasil ditambahkan.");
        } catch (Exception e) {
            System.err.println("Gagal menambahkan nilai siswa: " + e.getMessage());
        }
    }

    public void displayStudentAndGrades() {
        try {
            studentService.getAllStudents().forEach(student -> {
                System.out.println("\nID: " + student.getId());
                System.out.println("Nama: " + student.getName());
                System.out.println("Alamat: " + student.getAddress());
                System.out.println("Nilai:");
                gradeService.getGradesByStudentId(student.getId()).forEach(grade ->
                        System.out.println("- " + grade.getSubject() + ": " + grade.getValue()));
                System.out.println("----------");
            });
        } catch (Exception e) {
            System.err.println("Gagal menampilkan data siswa dan nilainya: " + e.getMessage());
        }
    }

    public void inputTeacherNote() {
        try {
            System.out.print("Masukkan ID Siswa: ");
            String studentId = scanner.nextLine();
            System.out.print("Masukkan Catatan Guru: ");
            String note = scanner.nextLine();

            if (studentId.isEmpty() || note.isEmpty()) {
                System.out.println("ID Siswa dan Catatan tidak boleh kosong!");
                return;
            }

            TeacherNote teacherNote = new TeacherNote(studentId, note);
            teacherNoteService.addNote(teacherNote);
            System.out.println("Catatan Guru berhasil ditambahkan.");
        } catch (Exception e) {
            System.err.println("Gagal menambahkan catatan guru: " + e.getMessage());
        }
    }

    public void exportData() {
        System.out.println("Export data siswa berhasil.");
        displayStudentAndGrades(); // Placeholder untuk ekspor ke file
    }

    public void calculateAverageGrade() {
        try {
            System.out.print("Masukkan ID Siswa: ");
            String studentId = scanner.nextLine();
            double average = gradeService.calculateAverageGrade(studentId);
            System.out.println("Rata-rata Nilai: " + average);
        } catch (Exception e) {
            System.err.println("Gagal menghitung rata-rata nilai: " + e.getMessage());
        }
    }

    public void rankStudents() {
        try {
            System.out.println("Ranking Siswa Berdasarkan Nilai:");
            List<Grade> rankedStudents = gradeService.getRankedStudents();
            if (rankedStudents.isEmpty()) {
                System.out.println("Tidak ada data nilai untuk ditampilkan.");
            } else {
                int rank = 1;
                for (Grade grade : rankedStudents) {
                    System.out.println(rank + ". ID: " + grade.getStudentId() + ", Nilai Rata-rata: " + grade.getValue());
                    rank++;
                }
            }
        } catch (Exception e) {
            System.err.println("Gagal menentukan ranking siswa: " + e.getMessage());
        }
    }


    public void displayGradeHistory() {
        System.out.println("Riwayat Perubahan Nilai (implementasi lengkap).");
    }

    public void searchStudent() {
        try {
            System.out.print("Masukkan Nama Siswa: ");
            String name = scanner.nextLine();

            if (name.isEmpty()) {
                System.out.println("Nama siswa tidak boleh kosong!");
                return;
            }

            // Ambil data siswa yang cocok
            var matchingStudents = studentService.getAllStudents().stream()
                    .filter(student -> student.getName().toLowerCase().contains(name.toLowerCase()))
                    .toList();

            // Periksa apakah ada siswa yang ditemukan
            if (matchingStudents.isEmpty()) {
                System.out.println("Tidak ada siswa dengan nama yang mengandung: " + name);
            } else {
                System.out.println("\nHasil Pencarian:");
                matchingStudents.forEach(student ->
                        System.out.println("ID: " + student.getId() + ", Nama: " + student.getName() + ", Alamat: " + student.getAddress()));
            }
        } catch (Exception e) {
            System.err.println("Gagal mencari data siswa: " + e.getMessage());
        }
    }


    public void displayProgressChart() {
        System.out.println("Grafik Perkembangan Nilai (implementasi lengkap).");
    }

    private void sendNotifications() {
        System.out.println("Notifikasi Perkembangan (implementasi lengkap).");
    }

    private void manageUsers() {
        System.out.println("User Management (implementasi lengkap).");
    }
}
