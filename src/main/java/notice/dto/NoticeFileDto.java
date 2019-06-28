package notice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticeFileDto {

    private Long idx;
    private String originalFileName;
    private String storedFilePath;
    private long fileSize;
    private String creatorId;
    private LocalDateTime createdDatetime;
    private String updaterId;

    @Builder
    public NoticeFileDto(Long idx, String originalFileName, String storedFilePath, long fileSize, String creatorId, LocalDateTime createdDatetime, String updaterId) {
        this.idx = idx;
        this.originalFileName = originalFileName;
        this.storedFilePath = storedFilePath;
        this.fileSize = fileSize;
        this.creatorId = creatorId;
        this.createdDatetime = createdDatetime;
        this.updaterId = updaterId;
    }

}
