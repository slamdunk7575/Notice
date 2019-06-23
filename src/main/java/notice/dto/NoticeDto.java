package notice.dto;

import lombok.Data;
import java.util.List;

@Data
public class NoticeDto {
    private int noticeIdx;
    private String title;
    private String contents;
    private int hitCnt;
    private String creatorId;
    private String createdDatetime;
    private String updatorId;
    private String updatedDatetime;
    private List<NoticeFileDto> fileList;
}
