package com.gussoft.mongomito.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dusi7ckpv",
                "api_key", "159954335886693",
                "api_secret", "ySXUlhR4G-WIEpLO25NvQ542FYs",
                "secure", true
        ));
    }
}
