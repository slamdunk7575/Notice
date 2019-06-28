package notice;

import notice.domain.NoticeEntity;
import notice.repository.NoticeRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoticeRepositoryTest {

    @Autowired
    NoticeRepository noticeRepository;

    @After
    public void creanUp() {
        noticeRepository.deleteAll();
    }

    @Test
    public void 게시글_저장_불러오기_테스트() {

        // given
        noticeRepository.save(NoticeEntity.builder()
                .title("테스트 공지")
                .contents("테스트 내용")
                .creatorId("slamdunk7575")
                .hitCnt(100000)
                .build());

        // when
        List<NoticeEntity> noticeList = noticeRepository.findAllByOrderByNoticeIdxDesc();

        // then
        NoticeEntity notice = noticeList.get(0);
        assertThat(notice.getTitle(), is("테스트 공지"));
        assertThat(notice.getContents(), is("테스트 내용"));
        assertThat(notice.getCreatorId(), is("slamdunk7575"));
    }


    @Test
    public void BaseTimeEntity_등록 () {

        // given
        LocalDateTime now = LocalDateTime.now();
        noticeRepository.save(NoticeEntity.builder()
                .title("시간 테스트")
                .contents("등록/업데이트 시간")
                .creatorId("slamdunk7575")
                .build());

        // when
        List<NoticeEntity> noticeList = noticeRepository.findAllByOrderByNoticeIdxDesc();

        //then
        NoticeEntity notice = noticeList.get(0);
        assertTrue(notice.getCreatedDatetime().isAfter(now));
        assertTrue(notice.getUpdatedDatetime().isAfter(now));

    }


}
