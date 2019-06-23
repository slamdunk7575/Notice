package notice.dto;

import lombok.Data;

@Data
public class NoticeFileDto {
    private int idx;
    private int noticeIdx;
    private String originalFileName;
    private String storedFilePath;
    private long fileSize;
}
