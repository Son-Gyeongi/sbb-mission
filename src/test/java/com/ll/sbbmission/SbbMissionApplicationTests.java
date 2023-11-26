package com.ll.sbbmission;

import com.ll.sbbmission.answer.Answer;
import com.ll.sbbmission.answer.AnswerRepository;
import com.ll.sbbmission.question.Question;
import com.ll.sbbmission.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest // 스프링부트 테스트 클래스임을 의미
class SbbMissionApplicationTests {

    @Autowired // 스프링의 DI 기능 : 스프링이 객체를 대신 생성하여 주입
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Transactional // 테스트가 종료될 때까지 DB 세션이 유지된다.
    @Test
        // 테스트 메서드임을 나타낸다.
    void testJpa() {
        Optional<Question> oq = this.questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        List<Answer> answerList = q.getAnswerList();

        assertEquals(1, answerList.size());
        assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
    }
}
