package notice.repository;

import notice.entity.NoticeEntity;
import notice.entity.NoticeFileEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeRepository extends CrudRepository<NoticeEntity, Integer> {

    List<NoticeEntity> findAllByOrderByNoticeIdxDesc();

    @Query("SELECT file FROM NoticeFileEntity file WHERE notice_idx = :noticeIdx AND idx = :idx")
    NoticeFileEntity findNoticeFile(@Param("noticeIdx") int noticeIdx, @Param("idx") int idx);

}
