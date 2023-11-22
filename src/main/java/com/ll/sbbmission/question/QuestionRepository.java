package com.ll.sbbmission.question;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    // 제목으로 조회
    Question findBySubject(String subject);

    // 제목, 내용으로 조회
    Question findBySubjectAndContent(String subject, String content);

    // 제목에 특정 문자열이 포함되어 있는 데이터 조회
    List<Question> findBySubjectLike(String subject);
}
