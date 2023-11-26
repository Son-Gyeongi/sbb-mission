package com.ll.sbbmission.question;

import com.ll.sbbmission.answer.AnswerForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;

    // 게시글 모두 불러오기
    @GetMapping("/list")
    public String list(Model model) {
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    // 질문 상세 페이지
    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id,
                         AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    // 질문 등록 하러 가기 GET
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) { // 매개변수의 형태가 다른 경우에 가능하다. - 메서드 오버로딩
        // QuestionForm과 같이 매개변수로 바인딩한 객체는 Model 객체로 전달하지 않아도 템플릿에서 사용이 가능

        return "question_form";
    }

    // 질문 등록하기 POST
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        // 오류 체크
        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        // 질문을 저장한다.
        this.questionService.create(questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/list"; // 질문 저장 후 질문 목록으로 이동
    }
}
