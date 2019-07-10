package notice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import notice.domain.NoticeEntity;
import notice.domain.NoticeFileEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;


@Data
@NoArgsConstructor
public class NoticeDto {

    private Long noticeIdx;
    private String title;
    private String contents;
    private int hitCnt;
    private String creatorId;
    private String updaterId;
    private String createdDatetime;
    private Collection<NoticeFileDto> fileList;


    public NoticeEntity toEntity() {
        String createdDatetimeCheck = Optional.ofNullable(this.createdDatetime).orElse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return NoticeEntity.builder()
                .noticeIdx(this.noticeIdx)
                .title(this.title)
                .contents(this.contents)
                .hitCnt(this.hitCnt)
                .creatorId("Admin")
                .createdDatetime(LocalDateTime.parse(createdDatetimeCheck, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .updaterId(this.updaterId)
                .build();
    }


    @Builder
    public NoticeDto(Long noticeIdx, String title, String contents, int hitCnt, String creatorId, String updaterId, String createdDatetime, Collection<NoticeFileEntity> fileList) {
        this.noticeIdx = noticeIdx;
        this.title = title;
        this.contents = contents;
        this.hitCnt = hitCnt;
        this.creatorId = creatorId;
        this.createdDatetime = createdDatetime;
        this.updaterId = updaterId;
        this.fileList = setNoticeFileDto(fileList);
    }


    public Collection<NoticeFileDto> setNoticeFileDto(Collection<NoticeFileEntity> noticeFileEntityCollection) {
        Collection<NoticeFileDto> fileList = new ArrayList<>();
        for (NoticeFileEntity noticeFileEntity : noticeFileEntityCollection) {
            fileList.add(noticeFileEntity.toDto());
        }
        return fileList;
    }

}
