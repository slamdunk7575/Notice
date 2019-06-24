package notice.service;

import notice.entity.NoticeRedis;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface NoticeRedisService {

    List<NoticeRedis> selectNoticeList() throws Exception;

    void saveNotice(NoticeRedis notice, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;

    NoticeRedis selectNoticeDetail(int noticeIdx) throws Exception;

    void deleteNotice(int noticeIdx);

    // NoticeFileEntity selectNoticeFileInformation(int noticeIdx, int idx) throws Exception;
}
