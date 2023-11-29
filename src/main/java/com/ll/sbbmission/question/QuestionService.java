package com.ll.sbbmission.question;

import com.ll.sbbmission.DataNotFoundException;
import com.ll.sbbmission.answer.Answer;
import com.ll.sbbmission.user.SiteUser;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    // 검색
    // Specification은 보다 정교한 쿼리의 작성을 도와주는 JPA의 도구
    private Specification<Question> search(String kw) { // kw는 keyword, 검색어
        // 검색어(kw)를 입력받아 쿼리의 조인문과 where문을 생성하여 리턴하는 메서드
        return new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // q - Root, 즉 기준을 의미하는 Question 엔티티의 객체 (질문 제목과 내용을 검색하기 위해 필요)
                query.distinct(true); // 중복을 제거
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT); // 질문 작성자를 검색
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT); // 답변 내용을 검색
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT); // 답변 작성자를 검색
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"), // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"), // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"), // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%")); // 답변 작성자
            }
        };
    }

    // 게시글 모두 불러오기
    public Page<Question> getList(int page, String kw) {
        // 작성일시 역순으로 조회하기
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate")); // 기준 createDate
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Question> spec = search(kw); // 검색
        return this.questionRepository.findAll(spec, pageable);
    }

    // 질문 상세 페이지 - 1개 조회
    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);

        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    // 질문 등록
    public void create(String subject, String content, SiteUser user) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        this.questionRepository.save(q);
    }

    // 질문 수정
    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    // 질문 삭제
    public void delete(Question question) {
        this.questionRepository.delete(question);
    }

    // 질문 추천인 저장
    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser); // Set의 add()
        this.questionRepository.save(question);
    }
}
