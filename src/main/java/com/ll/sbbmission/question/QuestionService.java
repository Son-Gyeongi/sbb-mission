package com.ll.sbbmission.question;

import com.ll.sbbmission.DataNotFoundException;
import com.ll.sbbmission.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    // 게시글 모두 불러오기
    public Page<Question> getList(int page) {
        // 작성일시 역순으로 조회하기
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate")); // 기준 createDate
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.questionRepository.findAll(pageable);
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
