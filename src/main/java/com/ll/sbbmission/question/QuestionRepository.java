package com.ll.sbbmission.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    // 쿼리에 익숙하다면 복잡한 쿼리는 자바코드로 생성하기 보다는 직접 쿼리를 작성, 엔티티 기준으로 작성
    @Query("select "
            + "distinct q "
            + "from Question q "
            + "left outer join SiteUser u1 on q.author=u1 "
            + "left outer join Answer a on a.question=q "
            + "left outer join SiteUser u2 on a.author=u2 "
            + "where "
            + "   q.subject like %:kw% "
            + "   or q.content like %:kw% "
            + "   or u1.username like %:kw% "
            + "   or a.content like %:kw% "
            + "   or u2.username like %:kw% ")
    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
}
