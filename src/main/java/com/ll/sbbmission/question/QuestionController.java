package com.ll.sbbmission.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    // 질문 등록 하러 가기 GET
    @GetMapping("/create")
    public String questionCreate() { // 매개변수의 형태가 다른 경우에 가능하다. - 메서드 오버로딩
        return "question_form";
    }

    // 질문 등록하기 POST
    @PostMapping("/create")
    public String questionCreate(@RequestParam String subject,
                                 @RequestParam String content) {
        // 질문을 저장한다.
        this.questionService.create(subject, content);
        return "redirect:/question/list"; // 질문 저장 후 질문 목록으로 이동
    }
}
