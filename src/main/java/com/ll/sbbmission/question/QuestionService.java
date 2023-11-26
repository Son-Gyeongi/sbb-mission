package com.ll.sbbmission.question;

import com.ll.sbbmission.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    // 게시글 모두 불러오기
    public List<Question> getList() {
        return this.questionRepository.findAll();
    }

    // 질문 상세 페이지 - 1개 조회
    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);

        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }
}
