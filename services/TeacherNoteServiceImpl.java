package services;

import entities.TeacherNote;
import repositories.TeacherNoteRepository;

public class TeacherNoteServiceImpl implements TeacherNoteService {
    private final TeacherNoteRepository teacherNoteRepository;

    public TeacherNoteServiceImpl(TeacherNoteRepository teacherNoteRepository) {
        this.teacherNoteRepository = teacherNoteRepository;
    }

    @Override
    public void addNote(TeacherNote note) {
        teacherNoteRepository.addNote(note);  // Menyimpan catatan ke database
    }
}
