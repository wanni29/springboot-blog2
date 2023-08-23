package shop.mtcoding.blogv2._core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

// 톰캣의 web.xml [xml파일] 
// => 너는 xml로 작성해 내가 자바 파일로 바꿔줄게
// pojo
// web.xml [문지기 문서] => application.yml 
// 확장자가 적혀있으면 static 폴더로 가!
// 사진 외부경로 설정법
// 문지기한테 문서 하나 더준다고 생각해
@Configuration
public class webMvcConfig implements WebMvcConfigurer{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry.addResourceHandler("/images/**")
        .addResourceLocations("file:" + "./images/")
        .setCachePeriod(10) // 10초
        .resourceChain(true)
        .addResolver(new PathResourceResolver());

    }


    



}
