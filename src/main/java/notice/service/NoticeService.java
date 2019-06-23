package notice.service;

import notice.entity.NoticeEntity;
import notice.entity.NoticeFileEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface NoticeService {

    List<NoticeEntity> selectNoticeList() throws Exception;

    void saveNotice(NoticeEntity notice, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;

    NoticeEntity selectNoticeDetail(int noticeIdx) throws Exception;

    void deleteNotice(int noticeIdx);

    NoticeFileEntity selectNoticeFileInformation(int noticeIdx, int idx) throws Exception;

}
