package by.bsuir.notes_service.repository;

import by.bsuir.notes_service.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findAllByUserIdAndDeletedAtIsNull(Long userId);

    long countByUserIdAndDeletedAtIsNull(Long userId);
}