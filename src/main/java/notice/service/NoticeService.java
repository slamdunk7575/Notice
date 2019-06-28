package notice.service;

import notice.domain.NoticeEntity;
import notice.domain.NoticeFileEntity;
import notice.dto.NoticeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface NoticeService {

    List<NoticeDto> selectNoticeList() throws Exception;

    Page<NoticeEntity> selectNoticeListWithPage(Pageable pageable) throws Exception;

    void saveNotice(NoticeDto notice, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;

    NoticeDto selectNoticeDetail(Long noticeIdx) throws Exception;

    void deleteNotice(Long noticeIdx);

    NoticeFileEntity selectNoticeFileInformation(Long noticeIdx, Long idx) throws Exception;

}
