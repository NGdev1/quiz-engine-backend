package com.university.itis.repository;

import com.university.itis.model.QuizParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizParticipantRepository extends JpaRepository<QuizParticipant, Long> {
    List<QuizParticipant> findAllByQuizId(Long quizId);
}