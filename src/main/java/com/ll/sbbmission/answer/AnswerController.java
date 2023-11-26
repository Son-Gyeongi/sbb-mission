package com.ll.sbbmission.answer;

import com.ll.sbbmission.question.Question;
import com.ll.sbbmission.question.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    // 답변 등록
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id,
                               @Valid AnswerForm answerForm, BindingResult bindingResult) {
        // 답변을 등록하려면 어떤 질문인지 알아야 한다. 없을 경우에는 404 오류가 발생
        Question question = this.questionService.getQuestion(id);

        if (bindingResult.hasErrors()) {
            // 검증에 실패할 경우에는 다시 답변을 등록할 수 있는 question_detail 템플릿을 렌더링
            model.addAttribute("question", question);
            return "question_detail";
        }

        // 답변을 저장한다.
        this.answerService.create(question, answerForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }
}
