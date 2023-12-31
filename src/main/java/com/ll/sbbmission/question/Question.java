package com.ll.sbbmission.question;

import com.ll.sbbmission.answer.Answer;
import com.ll.sbbmission.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity // JPA가 엔티티로 인식
public class Question {
    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 해당 컬럼만의 독립적인 시퀀스를 생성하여 번호를 증가
    private Integer id; // 고유번호

    @Column(length = 200)
    private String subject; // 제목

    @Column(columnDefinition = "TEXT")
    private String content; // 내용

    private LocalDateTime createDate; // 작성일시

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) // Question 하나에 Answer는 여러개
    private List<Answer> answerList;

    @ManyToOne
    private SiteUser author; // 글쓴이

    private LocalDateTime modifyDate; // 수정 일시

    @ManyToMany // 대등한 관계
    Set<SiteUser> voter; // 추천인, 추천인은 중복되면 안되기 때문에 Set을 썼다. Set은 중복을 허용하지 않는 자료형
}
