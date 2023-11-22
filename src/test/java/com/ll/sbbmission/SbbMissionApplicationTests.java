package com.ll.sbbmission;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest // 스프링부트 테스트 클래스임을 의미
class SbbMissionApplicationTests {

    @Autowired // 스프링의 DI 기능 : 스프링이 객체를 대신 생성하여 주입
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Test // 테스트 메서드임을 나타낸다.
    void testJpa() {
        Optional<Question> oq = this.questionRepository.findById(2);
        Assertions.assertTrue(oq.isPresent());
        Question q = oq.get();

        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q); // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
        a.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a);
    }
}
