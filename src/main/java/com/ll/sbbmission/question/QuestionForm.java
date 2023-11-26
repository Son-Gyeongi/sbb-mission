package com.ll.sbbmission.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
    @NotEmpty(message = "제목은 필수항목입니다.") // Null 또는 빈 문자열("")을 허용하지 않음
    @Size(max = 200) // 최대 길이가 200 바이트를 넘으면 안된다
    private String subject;

    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;
}
