package notice.service;

import notice.domain.NoticeRedis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface NoticeRedisService {

    List<NoticeRedis> selectNoticeList() throws Exception;

    void saveNotice(NoticeRedis notice, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;

    NoticeRedis selectNoticeDetail(int noticeIdx) throws Exception;

    void deleteNotice(int noticeIdx);

    // NoticeFileEntity selectNoticeFileInformation(int noticeIdx, int idx) throws Exception;

    Page<NoticeRedis> selectNoticeListWithPage(Pageable pageable) throws Exception;
}
