package notice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@RedisHash("noticeFile")
public class NoticeFileRedis implements Serializable {

    @Id
    @Indexed
    private int idx;
    private String originalFileName;
    private String storedFilePath;
    private long fileSize;
    private String creatorId;
    private LocalDateTime createdDatetime = LocalDateTime.now();
    private String updaterId;
    private LocalDateTime updatedDatetime;
    private int noticeIdx;

    @Builder
    public NoticeFileRedis(String originalFileName, String storedFilePath, long fileSize, String creatorId, LocalDateTime createdDatetime, int noticeIdx) {
        this.originalFileName = originalFileName;
        this.storedFilePath = storedFilePath;
        this.fileSize = fileSize;
        this.creatorId = creatorId;
        this.createdDatetime = createdDatetime;
        this.noticeIdx = noticeIdx;
    }
}
