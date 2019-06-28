package notice.service;

import notice.domain.NoticeRedis;
import notice.repository.NoticeRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeRedisServiceImpl implements NoticeRedisService {

    private NoticeRedisRepository noticeRedisRepository;

    @Autowired
    public NoticeRedisServiceImpl(NoticeRedisRepository noticeRedisRepository) {
        this.noticeRedisRepository = noticeRedisRepository;
    }

    @Autowired
    RedisTemplate redisTemplate;

    private RedisAtomicInteger uniqueNum = null;

    @PostConstruct
    public void init() {
        uniqueNum = new RedisAtomicInteger("RedisUniqueNum", redisTemplate.getConnectionFactory(), 1);
    }

    @Override
    public List<NoticeRedis> selectNoticeList() throws Exception {
         return noticeRedisRepository.findAllByOrderByNoticeIdxDesc();
    }

    @Override
    public Page<NoticeRedis> selectNoticeListWithPage(Pageable pageable) throws Exception {
        // Pageable의 page는 index 처럼 0 부터 시작
        // 주로 게시판에서는 1 부터 시작하기 때문에 사용자가 보려는 페이지에서 -1 처리
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        // pageable = PageRequest.of(page, 10);
        pageable = PageRequest.of(page, 10, new Sort(Sort.Direction.DESC, "notice"));
        // pageable = PageRequest.of(page, 10, Sort.by("noticeIdx").descending());

        return noticeRedisRepository.findAllByOrderByNoticeIdxDesc(pageable);
    }


    @Override
    public void saveNotice(NoticeRedis notice, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {

        String id = "admin";
        LocalDateTime now = LocalDateTime.now();
        notice.setNoticeIdx(uniqueNum.getAndIncrement());
        notice.setCreatorId(id);
        notice.setCreatedDatetime(now);
        notice.setHitCnt(new Integer(0));

        noticeRedisRepository.save(notice);
    }


    @Override
    public NoticeRedis selectNoticeDetail(int noticeIdx) throws Exception {
        Optional<NoticeRedis> optional = noticeRedisRepository.findById(noticeIdx);
        if (optional.isPresent()) {
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
