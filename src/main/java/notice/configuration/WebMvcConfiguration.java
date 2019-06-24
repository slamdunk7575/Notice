package notice.configuration;

import notice.entity.NoticeRedis;
import notice.interceptor.LoggerInterceptor;
import notice.repository.NoticeRedisRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.util.Arrays;


@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private NoticeRedisRepository noticeRedisRepository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerInterceptor());
    }


    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        // 업로드되는 파일의 크기를 제한. 바이트(Byte) 단위로 설정할 수 있음. 5mb로 설정
        commonsMultipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024);
        return commonsMultipartResolver;
    }



//   스프링 부트 2.1.X 버전에는 이미 인코딩 필터가 적용되어 있음
//   2.1.X 보다 아래 버전을 쓰거나 다른 인코딩 필터를 추가해야 하는 경우 사용
//    @Bean
//    public Filter characterEncodingFilter() {
//
//        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//        characterEncodingFilter.setEncoding("UTF-8");
//        characterEncodingFilter.setForceEncoding(true);
//
//        return characterEncodingFilter;
//    }
//
//    @Bean
//    public HttpMessageConverter<String> responseBodyConverter() {
//        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
//    }

}
