package com.ll.sbbmission;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest // 스프링부트 테스트 클래스임을 의미
class SbbMissionApplicationTests {

    @Autowired // 스프링의 DI 기능 : 스프링이 객체를 대신 생성하여 주입
    private QuestionRepository questionRepository;

    @Test // 테스트 메서드임을 나타낸다.
    void testJpa() {
        Optional<Question> oq = this.questionRepository.findById(1);
        if (oq.isPresent()) {
            Question q = oq.get();
            assertEquals("sbb가 무엇인가요?", q.getSubject());
        }
    }

}
