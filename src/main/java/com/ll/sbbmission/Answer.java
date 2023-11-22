package com.ll.sbbmission;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
}
