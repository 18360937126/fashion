package cn.hysf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //所有访问/admin/的都要去指定的位置下找资源
        registry.addResourceHandler("/homePage/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/goods/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/goodsPage/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/cartPage/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/cart/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/wishlistPage/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/wishlist/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/orders/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/ordersPage/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/comment/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/commentPage/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/declarePage/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/admin/**").addResourceLocations("classpath:/static/");
    }

}