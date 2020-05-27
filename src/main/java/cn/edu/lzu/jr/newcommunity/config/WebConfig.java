package cn.edu.lzu.jr.newcommunity.config;

import cn.edu.lzu.jr.newcommunity.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration

//        先看容器中有没有用户自己配置的（@Bean、@Component）有:就用用户配置的 没有: 自动配置；
//        组件有多个（如ViewResolver）:将用户配置的和容器默认的组合起来；
//        在SpringBoot中会有非常多的xxxConfigurer帮助我们进行扩展配置
//        在SpringBoot中会有很多的xxxCustomizer帮助我们进行定制配置

//@EnableWebMvc  让所有的SpringMVC的自动配置都失效了，使用用户自己的配置 尽量不要开启
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] excludes = new String[]{"/","/static/**"};
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**").excludePathPatterns(excludes);
    }
}
