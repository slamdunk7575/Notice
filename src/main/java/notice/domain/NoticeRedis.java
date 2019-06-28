package notice.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@RedisHash("notice")
public class NoticeRedis implements Serializable, Comparable<NoticeRedis> {

    @Id
    @Indexed
    private Integer noticeIdx;
    private String contents;
    private Integer hitCnt;
    private String title;
    private String creatorId;
    private LocalDateTime createdDatetime = LocalDateTime.now();
    private String updaterId;
    private LocalDateTime updatedDatetime;

    @Builder
    public NoticeRedis(Integer noticeIdx, String title, String contents, Integer hitCnt, String creatorId, LocalDateTime createdDatetime) {
        this.noticeIdx = noticeIdx;
        this.title = title;
        this.contents = contents;
        this.hitCnt = hitCnt;
        this.creatorId = creatorId;
        this.createdDatetime = createdDatetime;
    }

    public void refresh(Integer plusHitCnt) {
        this.hitCnt = plusHitCnt;
    }

    @Override
    public int compareTo(NoticeRedis n) {
        if (this.noticeIdx < n.getNoticeIdx()) {
            return -1;
        } else if (this.noticeIdx > n.noticeIdx) {
            return 1;
        }
        return 0;
    }
}
