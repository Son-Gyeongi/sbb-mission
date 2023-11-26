package com.ll.sbbmission.answer;

import com.ll.sbbmission.question.Question;
import com.ll.sbbmission.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    // 답변 등록
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam String content) {
        // content는 question_detail의 textarea의 name인 content와 같아야 한다.
        // 답변을 등록하려면 어떤 질문인지 알아야 한다. 없을 경우에는 404 오류가 발생
        Question question = this.questionService.getQuestion(id);
        // 답변을 저장한다.
        this.answerService.create(question, content);
        return String.format("redirect:/question/detail/%s", id);
    }
}
