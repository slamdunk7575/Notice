package notice.domain;

import lombok.*;
import notice.dto.NoticeDto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Optional;

@Entity
@Getter
@Table(name = "t_notice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class NoticeEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long noticeIdx;

    @Column(length = 500, nullable = false)
    private String title;

    // 테이블의 컬럼정보와 엔티티를 맞춰줘야함
    @Column(columnDefinition = "TEXT", nullable = false)
    private String contents;

    @Column(nullable = false)
    private int hitCnt = 0;

    @Column(nullable = false)
    private String creatorId;

    private String updaterId;

    @Setter
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="notice_idx")
    private Collection<NoticeFileEntity> fileList;


    @Builder
    public NoticeEntity(Long noticeIdx, String title, String contents, int hitCnt, String creatorId, LocalDateTime createdDatetime, String updaterId, Collection<NoticeFileEntity> fileList) {
        this.noticeIdx = noticeIdx;
        this.title = title;
        this.contents = contents;
        this.hitCnt = hitCnt;
        this.creatorId = creatorId;
        this.setCreatedDatetime(createdDatetime);
        this.updaterId = updaterId;
        this.fileList = fileList;
    }


    public NoticeDto toDto() {
        LocalDateTime createdDatetimeCheck = Optional.ofNullable(this.getCreatedDatetime())
                .orElseGet(() -> LocalDateTime.now());
        return NoticeDto.builder()
                .noticeIdx(this.getNoticeIdx())
                .title(this.title)
                .contents(this.contents)
                .hitCnt(this.hitCnt)
                .creatorId(this.creatorId)
                .createdDatetime(createdDatetimeCheck.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .updaterId(this.updaterId)
                .fileList(this.fileList)
                .build();
    }

    public void refresh(Integer plusHitCnt) {
        this.hitCnt = plusHitCnt;
    }

}


