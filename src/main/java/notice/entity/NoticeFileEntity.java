package notice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="t_notice_file")
@NoArgsConstructor
@Data
public class NoticeFileEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idx;

    @Column(nullable=false)
    private String originalFileName;

    @Column(nullable=false)
    private String storedFilePath;

    @Column(nullable=false)
    private long fileSize;

    @Column(nullable=false)
    private String creatorId;

    @Column(nullable=false)
    private LocalDateTime createdDatetime = LocalDateTime.now();

    private String updaterId;

    private LocalDateTime updatedDatetime;

}
