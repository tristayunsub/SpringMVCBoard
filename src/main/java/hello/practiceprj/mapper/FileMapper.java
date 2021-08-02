package hello.practiceprj.mapper;

import hello.practiceprj.domain.UploadFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO BOARDFILE VALUES(#{fileNum}, #{boardId}, #{storeFileName}, #{uploadFileName}, #{fileSize})")
    void uploadFile(UploadFile uploadFile);

    @Select("SELECT * FROM BOARDFILE WHERE BOARDID = #{boardId}")
    List<UploadFile> getFiles(int boardId);
}
