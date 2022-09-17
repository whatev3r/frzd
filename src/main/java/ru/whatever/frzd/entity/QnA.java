package ru.whatever.frzd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "qna")
@Getter
@NoArgsConstructor
public class QnA extends BaseEntity {

    @Column(name = "question")
    private String question;

    @Column(name = "answer")
    private String answer;
}
