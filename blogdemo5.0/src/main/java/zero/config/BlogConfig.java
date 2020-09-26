package zero.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BlogConfig implements WebMvcConfigurer{

    // 资源拦截器,拦截不合法的路径访问
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/fonts/**")
                .excludePathPatterns("/images/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/plugins/editor/**")  // ** 匹配多级路径
                .excludePathPatterns("/")
                .excludePathPatterns("/login")
                .excludePathPatterns("/a/*")  // * 只能匹配一级路径,如: /a/1, /a/1/2
                .excludePathPatterns("/register")
                .excludePathPatterns("/register/*")
                .excludePathPatterns("/delete/*")
                .excludePathPatterns("/change")
                .excludePathPatterns("/getVerify");
    }
}
