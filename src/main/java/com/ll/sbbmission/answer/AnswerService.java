package com.ll.sbbmission.answer;

import com.ll.sbbmission.DataNotFoundException;
import com.ll.sbbmission.question.Question;
import com.ll.sbbmission.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    // 답변 등록
    public void create(Question question, String content, SiteUser author) {
        // 입력으로 받은 question과 content, author를 사용하여 Answer 객체를 생성하여 저장
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        this.answerRepository.save(answer);
    }

    // 답변 조회
    public Answer getAnswer(Integer id) {
        // 답변 찾기
        Optional<Answer> answer = this.answerRepository.findById(id);

        // null 확인
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    // 답변 수정
    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

    // 답변 삭제
    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }

    // 답변 추천인 저장
    public void vote(Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);
        this.answerRepository.save(answer);
    }
}
