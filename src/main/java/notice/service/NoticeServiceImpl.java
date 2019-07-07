package notice.service;

import lombok.AllArgsConstructor;
import notice.common.FileUtils;
import notice.domain.NoticeEntity;
import notice.domain.NoticeFileEntity;
import notice.dto.NoticeDto;
import notice.repository.NoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private NoticeRepository noticeRepository;
    private FileUtils fileUtils;


    @Override
    public List<NoticeDto> selectNoticeList() throws Exception {
        List<NoticeEntity> noticeEntityList = noticeRepository.findAllByOrderByNoticeIdxDesc();
        /*List<NoticeDto> noticeDtoList = new ArrayList<>();
        for (NoticeEntity noticeEntity: noticeEntityList) {
            noticeDtoList.add(noticeEntity.toDto());
        }*/

        List<NoticeDto> noticeDtoList = noticeEntityList.stream().map(new Function<NoticeEntity, NoticeDto>() {
            @Override
            public NoticeDto apply(NoticeEntity noticeEntity) {
                return noticeEntity.toDto();
            }
        }).collect(Collectors.toList());

        return noticeDtoList;
    }

    @Override
    public Page<NoticeEntity> selectNoticeListWithPage(Pageable pageable) throws Exception {

        // Pageable의 page는 index 처럼 0 부터 시작
        // 주로 게시판에서는 1 부터 시작하기 때문에 사용자가 보려는 페이지에서 -1 처리
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 10, new Sort(Sort.Direction.DESC, "noticeIdx"));
        return noticeRepository.findAllByOrderByNoticeIdxDesc(pageable);
    }

    @Override
    public void saveNotice(NoticeDto noticeDto,
                           MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {

        NoticeEntity notice = noticeDto.toEntity();
        List<NoticeFileEntity> list = fileUtils.parseFileInfo(multipartHttpServletRequest);
        if(CollectionUtils.isEmpty(list) == false) {
            notice.setFileList(list);
        }
        noticeRepository.save(notice);
    }


    @Override
    public NoticeDto selectNoticeDetail(Long noticeIdx) throws Exception {
        Optional<NoticeEntity> optional = noticeRepository.findById(noticeIdx);
        if(optional.isPresent()) {
            NoticeEntity notice = optional.get();
            notice.refresh(notice.getHitCnt() + 1);
            noticeRepository.save(notice);

            NoticeDto noticeDto = notice.toDto();

            return noticeDto;
        } else {
            throw new NullPointerException();
        }
    }


    @Override
    public void deleteNotice(Long noticeIdx) {
        noticeRepository.deleteById(noticeIdx);
    }


    @Override
    public NoticeFileEntity selectNoticeFileInformation(Long noticeIdx, Long idx) throws Exception {
        NoticeFileEntity noticeFile = noticeRepository.findNoticeFile(noticeIdx, idx);
        return noticeFile;
    }

}
