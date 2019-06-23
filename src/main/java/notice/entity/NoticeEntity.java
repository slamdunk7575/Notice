package notice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "t_notice")
@NoArgsConstructor
@Data
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int noticeIdx;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private int hitCnt = 0;

    @Column(nullable = false)
    private String creatorId;

    @Column(nullable = false)
    private LocalDateTime createdDatetime = LocalDateTime.now();

    private String updaterId;

    private LocalDateTime updatedDatetime;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="notice_idx")
    private Collection<NoticeFileEntity> fileList;

}
