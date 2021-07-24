package hello.practiceprj.mapper;

import hello.practiceprj.domain.Board;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class TimeMapperTest {
//    private static final Logger log = LoggerFactory.getLogger(TimeMapperTest.class);
    @Autowired
    private TimeMapper timeMapper;
    @Test
    public void testGetTime(){
        String time = timeMapper.getTime();
        System.out.println(time);
        Board board = new Board();
        board.setTitle("dd");
        System.out.println(board.getTitle());
    }
    @Test
    public void testGetTime2(){
        String time2 = timeMapper.getTime2();
        log.info("개씨발");
    }


}