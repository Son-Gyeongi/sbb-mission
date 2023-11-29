package com.ll.sbbmission.answer;

import com.ll.sbbmission.question.Question;
import com.ll.sbbmission.question.QuestionService;
import com.ll.sbbmission.user.SiteUser;
import com.ll.sbbmission.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    // 답변 등록
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id,
                               @Valid AnswerForm answerForm, BindingResult bindingResult,
                               Principal principal) {
        // BindingResult : 답변을 등록하려면 어떤 질문인지 알아야 한다. 없을 경우에는 404 오류가 발생
        // Principal : 현재 로그인한 사용자에 대한 정보를 알기 위해서는 스프링 시큐리티가 제공하는 Principal 객체를 사용

        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());// 회원 조회, principal 객체를 통해 사용자명 얻었다.

        if (bindingResult.hasErrors()) {
            // 검증에 실패할 경우에는 다시 답변을 등록할 수 있는 question_detail 템플릿을 렌더링
            model.addAttribute("question", question);
            return "question_detail";
        }

        // 답변을 저장한다.
        this.answerService.create(question, answerForm.getContent(), siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }

    // 답변 수정 GET
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id,
                               Principal principal) {
        Answer answer = this.answerService.getAnswer(id);

        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        // 답변 수정시 기존의 내용이 필요하므로 AnswerForm 객체에 조회한 값을 저장
        answerForm.setContent(answer.getContent());
        return "answer_form";
    }

    // 답변 수정 POST
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
                               @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }

        // 답변 찾기
        Answer answer = this.answerService.getAnswer(id);

        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        this.answerService.modify(answer, answerForm.getContent());
        // 답변 수정을 완료한 후에는 질문 상세 페이지로 돌아가기 위해 answer.getQuestion.getId()로 질문의 아이디를 가져왔다.
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }

    // 답변 삭제
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
        Answer answer = this.answerService.getAnswer(id);

        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }

        this.answerService.delete(answer);
        // 답변을 삭제한 후에는 해당 답변이 있던 질문상세 화면으로 리다이렉트
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }
}
