package notice.service;

import notice.common.FileUtils;
import notice.entity.NoticeEntity;
import notice.entity.NoticeFileEntity;
import notice.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeServiceImpl implements NoticeService {

    private NoticeRepository noticeRepository;
    private FileUtils fileUtils;

    @Autowired
    public NoticeServiceImpl(NoticeRepository noticeRepository, FileUtils fileUtils) {
        this.noticeRepository = noticeRepository;
        this.fileUtils = fileUtils;
    }


    @Override
    public List<NoticeEntity> selectNoticeList() throws Exception {
        return noticeRepository.findAllByOrderByNoticeIdxDesc();
    }

    @Override
    public void saveNotice(NoticeEntity notice,
                           MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {

        notice.setCreatorId("admin");
        List<NoticeFileEntity> list = fileUtils.parseFileInfo(multipartHttpServletRequest);
        if(CollectionUtils.isEmpty(list) == false) {
            notice.setFileList(list);
        }
        noticeRepository.save(notice);
    }

    @Override
    public NoticeEntity selectNoticeDetail(int noticeIdx) throws Exception {
        Optional<NoticeEntity> optional = noticeRepository.findById(noticeIdx);
        if(optional.isPresent()) {
            NoticeEntity notice = optional.get();
            notice.setHitCnt(notice.getHitCnt() + 1);
            noticeRepository.save(notice);

            return notice;
        } else {
            throw new NullPointerException();
            // TODO 적절한 처리 필요
        }
    }

    @Override
    public void deleteNotice(int noticeIdx) {
        noticeRepository.deleteById(noticeIdx);
    }

    @Override
    public NoticeFileEntity selectNoticeFileInformation(int noticeIdx, int idx) throws Exception {
        NoticeFileEntity noticeFile = noticeRepository.findNoticeFile(noticeIdx, idx);
        return noticeFile;
    }

}
