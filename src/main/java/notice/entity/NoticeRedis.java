package notice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@RedisHash("notice")
public class NoticeRedis implements Serializable {

    @Id
    private Integer noticeIdx;
    private String contents;
    private Integer hitCnt;
    private String title;
    private String creatorId;
    private LocalDateTime createdDatetime = LocalDateTime.now();
    private String updaterId;
    private LocalDateTime updatedDatetime;

    @Builder
    public NoticeRedis(String title, String contents, Integer hitCnt, String creatorId, LocalDateTime createdDatetime) {
        this.title = title;
        this.contents = contents;
        this.hitCnt = hitCnt;
        this.creatorId = creatorId;
        this.createdDatetime = createdDatetime;
    }

    public void refresh(Integer plusHitCnt) {
        this.hitCnt = plusHitCnt;
    }
}
