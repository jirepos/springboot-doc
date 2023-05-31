package com.kyoofus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/** SpringMVC 커스터마이징 설정 클래스이다. */
@Configuration
public class DispatcherConfig implements WebMvcConfigurer {


  /** default servlet을 활성화 한다.  */
  public void configureDefaultServletHandling(
      org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  /**
   * src/main/resources/public, src/main/resources/static 정적 리소스 폴더를 사용할 수 있도록
   * 설정한다.
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // /public/**, /static/**  url 정적리소스트 매핑  
    registry.addResourceHandler("/public/**").addResourceLocations("classpath:/public/");// .setCachePeriod(0);
    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");// .setCachePeriod(0);
  }
}/// ~
