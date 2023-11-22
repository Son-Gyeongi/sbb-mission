package com.ll.sbbmission;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    // 제목으로 조회
    Question findBySubject(String subject);

    // 제목, 내용으로 조회
    Question findBySubjectAndContent(String subject, String content);
}
