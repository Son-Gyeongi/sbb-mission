package com.ll.sbbmission.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    // 제목으로 조회
    Question findBySubject(String subject);

    // 제목, 내용으로 조회
    Question findBySubjectAndContent(String subject, String content);

    // 제목에 특정 문자열이 포함되어 있는 데이터 조회
    List<Question> findBySubjectLike(String subject);

    // 페이징
    Page<Question> findAll(Pageable pageable);

    // 작성한 Specification을 사용하기 위해서
    Page<Question> findAll(Specification<Question> spec, Pageable pageable);
}
