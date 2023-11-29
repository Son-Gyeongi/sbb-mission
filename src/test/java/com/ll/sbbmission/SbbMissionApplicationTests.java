package com.ll.sbbmission;

import com.ll.sbbmission.question.QuestionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest // 스프링부트 테스트 클래스임을 의미
class SbbMissionApplicationTests {

    @Autowired
    private QuestionService questionService;

    @DisplayName("대량 테스트 데이터 만들기")
    @Test
        // 테스트 메서드임을 나타낸다.
    void testJpa() {
        for (int i = 1; i <= 300; i++) {
            String subject = String.format("테스트 데이터입니다.:[%03d], i");
            String content = "내용무";
            this.questionService.create(subject, content, null);
        }
    }
}
