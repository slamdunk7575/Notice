package notice.domain;

import lombok.*;
import notice.dto.NoticeFileDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="t_notice_file")
@NoArgsConstructor
@EqualsAndHashCode
public class NoticeFileEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long idx;

    @Column(nullable=false)
    private String originalFileName;

    @Column(nullable=false)
    private String storedFilePath;

    @Column(nullable=false)
    private long fileSize;

    @Column(nullable=false)
    private String creatorId;

    private String updaterId;


    @Builder
    public NoticeFileEntity(Long idx, String originalFileName, String storedFilePath, long fileSize, String creatorId, String updaterId) {
        this.idx = idx;
        this.originalFileName = originalFileName;
        this.storedFilePath = storedFilePath;
        this.fileSize = fileSize;
        this.creatorId = creatorId;
        this.updaterId = updaterId;
    }

    public NoticeFileDto toDto() {
        return NoticeFileDto.builder()
                .idx(this.idx)
                .originalFileName(this.originalFileName)
                .storedFilePath(this.storedFilePath)
                .fileSize(this.fileSize)
                .creatorId(this.creatorId)
                .createdDatetime(this.getCreatedDatetime())
                .updaterId(this.updaterId)
                .build();
    }

}
