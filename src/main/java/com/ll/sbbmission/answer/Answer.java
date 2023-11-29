package com.ll.sbbmission.answer;

import com.ll.sbbmission.question.Question;
import com.ll.sbbmission.user.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    /*
    답변은 Many(많은 것)가 되고 질문은 One(하나)이 된다. 따라서 @ManyToOne은 N:1 관계
     */
    @ManyToOne
    private Question question;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate; // 수정 일시

    @ManyToMany // 대등한 관계
    Set<SiteUser> voter; // 추천인, 추천인은 중복되면 안되기 때문에 Set을 썼다. Set은 중복을 허용하지 않는 자료형
}
