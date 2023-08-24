package shop.mtcoding.blogv2._core.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import shop.mtcoding.blogv2._core.filter.MyFilter1;

public class FilterConfig {

    // 필터를 등록하는 코드
    // FilterRegistrationBean<?> 디스패처 서블릿 바로옆에 붙어주는경우

    //@Bean
    //public FilterRegistrationBean<?> myFilter1() {
    //    FilterRegistrationBean<?> bean = new FilterRegistrationBean<>();
    //    bean.addUrlPatterns("/*"); // 슬래시로 시작하는 모든 주소에 발동됨
    //    bean.setOrder(0); // 낮은 번호부터 실행됨
    //    return bean;// 리턴값이 IoC에 등록된다.
    //}

    @Bean
    public FilterRegistrationBean<MyFilter1> myFilter1() {
        FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<MyFilter1>();
        bean.addUrlPatterns("/*"); // 슬래시로 시작하는 모든 주소에 발동됨
        bean.setOrder(0); // 낮은 번호부터 실행됨
        return bean;// 리턴값이 IoC에 등록된다.
    }

}
