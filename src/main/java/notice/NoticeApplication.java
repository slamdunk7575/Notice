package notice;

import notice.domain.NoticeEntity;
import notice.repository.NoticeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@EnableJpaAuditing
@EntityScan(
        basePackageClasses = {Jsr310JpaConverters.class},
        basePackages = {"notice.*"}
)
@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
public class NoticeApplication {

    public static void main(String[] args) {

        SpringApplication.run(NoticeApplication.class, args);
    }


    @Bean
    public CommandLineRunner initData(NoticeRepository noticeRepository) {
        return args ->
                IntStream.rangeClosed(1, 154).forEach(i -> {

                    NoticeEntity noticeEntity = NoticeEntity.builder()
                            .title("Notice 샘플 DATA")
                            .contents("페이징 TEST")
                            .creatorId("Admin")
                            .hitCnt(new Integer(0))
                            .createdDatetime(LocalDateTime.now())
                            .build();
                    noticeRepository.save(noticeEntity);
                });
    }

}
