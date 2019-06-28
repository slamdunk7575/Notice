package notice.repository;

import notice.domain.NoticeEntity;
import notice.domain.NoticeFileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

    List<NoticeEntity> findAllByOrderByNoticeIdxDesc();

    Page<NoticeEntity> findAllByOrderByNoticeIdxDesc(Pageable pageable);

    @Query("SELECT file FROM NoticeFileEntity file WHERE notice_idx = :noticeIdx AND idx = :idx")
    NoticeFileEntity findNoticeFile(@Param("noticeIdx") Long noticeIdx, @Param("idx") Long idx);

}
