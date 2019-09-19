package com.java.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@ConfigurationProperties(prefix = "runfkj.global-params")
@Component
@Data
public class GlobalParams {

    //静态路由映射的文件地址
    private StaticRouter staticRouter;
    
    private String imgUrlPrefix;
    
    private TencentCOSStorageConfig tencentCOSStorageConfig;

    @Data
    public static class StaticRouter{
        private String devHtml;
        private String devVideo;
        private String devUpload;
        private String prodHtml;
        private String prodVideo;
        private String prodUpload;
    }
    
    /**
     * 云存储配置信息
     *
     */
    @Data
    public  static  class TencentCOSStorageConfig implements Serializable {
        private static final long serialVersionUID = 1L;
        //腾讯云绑定的域名
        private String qcloudDomain;
        //腾讯云路径前缀
        private String qcloudPrefix;
        //腾讯云AppId
        private Integer qcloudAppId;
        //腾讯云SecretId
        private String qcloudSecretId;
        //腾讯云SecretKey
        private String qcloudSecretKey;
        //腾讯云BucketName
        private String qcloudBucketName;
        //腾讯云COS所属地区
        private String qcloudRegion;
    }   
}