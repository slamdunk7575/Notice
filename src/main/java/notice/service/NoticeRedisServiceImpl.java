package notice.service;

import notice.entity.NoticeEntity;
import notice.entity.NoticeFileEntity;
import notice.entity.NoticeRedis;
import notice.repository.NoticeRedisRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeRedisServiceImpl implements NoticeRedisService {

    private NoticeRedisRepository noticeRedisRepository;

    @Autowired
    public NoticeRedisServiceImpl(NoticeRedisRepository noticeRedisRepository) {
        this.noticeRedisRepository = noticeRedisRepository;
    }


    /*public void initData() {
        String id = "slamdunk7575";
        LocalDateTime now = LocalDateTime.now();
        NoticeRedis noticeRedis = NoticeRedis.builder()
                .title("Redis 초기 DATA 테스트")
                .contents("Redis 테스트중")
                .creatorId(id)
                .createdDatetime(now)
                .build();

        noticeRedisRepository.save(noticeRedis);
    }*/

    @Override
    public List<NoticeRedis> selectNoticeList() throws Exception {

        /*List<NoticeRedis> noticeList = new ArrayList<NoticeRedis>();
        Iterable<NoticeRedis> noticeRedisIterable = noticeRedisRepository.findAll();
        while (noticeRedisIterable.iterator().hasNext()) {
            noticeList.add(noticeRedisIterable.iterator().next());
        }*/
        return noticeRedisRepository.findAllByOrderByNoticeIdxDesc();
    }


    @Override
    public void saveNotice(NoticeRedis notice, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {

        String id = "admin";
        LocalDateTime now = LocalDateTime.now();
        notice.setCreatorId(id);
        notice.setCreatedDatetime(now);
        notice.setHitCnt(new Integer(0));

        // TODO 첨부파일 저장

        noticeRedisRepository.save(notice);
    }


    @Override
    public NoticeRedis selectNoticeDetail(int noticeIdx) throws Exception {
        Optional<NoticeRedis> optional = noticeRedisRepository.findById(noticeIdx);
        if(optional.isPresent()) {
            // NoticeRedis notice = optional.get();
            NoticeRedis notice = noticeRedisRepository.findById(noticeIdx).get();
            // notice.setHitCnt(notice.getHitCnt().intValue() + 1);
            notice.refresh(notice.getHitCnt() + 1);
            noticeRedisRepository.save(notice);

            return notice;
        } else {
            throw new NullPointerException();
        }
    }


    @Override
    public void deleteNotice(int noticeIdx) {
        noticeRedisRepository.deleteById(noticeIdx);
    }

}
