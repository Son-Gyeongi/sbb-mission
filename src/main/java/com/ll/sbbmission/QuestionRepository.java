package com.ll.sbbmission;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    // 제목으로 조회
    Question findBySubject(String subject);
}
