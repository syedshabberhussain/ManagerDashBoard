package com.campus1.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//public class WebConfig implements WebMvcConfigurer {
//
//    @Value("${profile.images.path}")
//    private String imageDir;
//
//    @Value("${profile.images.url}")
//    private String imageUrl;
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // Map the URL prefix to the physical folder
//        registry.addResourceHandler(imageUrl + "**")
//                .addResourceLocations("file:" + imageDir);
//    }
//
//}
