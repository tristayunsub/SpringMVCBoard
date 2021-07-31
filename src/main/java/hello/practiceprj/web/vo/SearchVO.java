package hello.practiceprj.web.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
public class SearchVO {
    public SearchVO(String searchText, String searchCategory) {
        this.searchText = searchText;
        this.searchCategory = searchCategory;
    }

    private String searchText;
    private String searchCategory;

    public SearchVO() {
    }
}
