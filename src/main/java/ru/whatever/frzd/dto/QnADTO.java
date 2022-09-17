package ru.whatever.frzd.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.whatever.frzd.entity.QnA;

@Getter
@Setter
@EqualsAndHashCode
public class QnADTO {

    public QnADTO(QnA qna) {
        this.question = qna.getQuestion();
        this.answer = qna.getAnswer();
    }

    public QnADTO(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    private String question;
    private String answer;
}
