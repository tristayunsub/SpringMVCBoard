package hello.practiceprj.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter @ToString
public class Recommend {
    private int boardId;
    private String likeId;

    public Recommend() {
    }

    public Recommend(int boardId, String likeId) {
        this.boardId = boardId;
        this.likeId = likeId;
    }
}
