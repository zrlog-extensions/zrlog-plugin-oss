package com.zrlog.plugin.oss.service;

import com.google.gson.annotations.SerializedName;

public class OssStorageConfig {

    private String bucket;
    @SerializedName("private_bucket")
    private String privateBucket;
    @SerializedName("access_key")
    private String accessKey;
    @SerializedName("secret_key")
    private String secretKey;
    private String host;
    private String region;
    private String supportHttps;
    private String syncTemplate;
    private String syncHtml;
    private String cacheMap;
    private String version;

    public String bucketName(String bucketKeyName) {
        if ("private_bucket".equals(bucketKeyName)) {
            return privateBucket;
        }
        return bucket;
    }

    public boolean isSupportHttpsEnabled() {
        return "on".equalsIgnoreCase(supportHttps);
    }

    public boolean isSyncTemplateEnabled() {
        return "on".equals(syncTemplate);
    }

    public boolean isSyncHtmlEnabled() {
        return "on".equals(syncHtml);
    }

    public void normalizeForPage(String version) {
        this.syncHtml = switchValue(syncHtml);
        this.syncTemplate = switchValue(syncTemplate);
        this.supportHttps = switchValue(supportHttps);
        this.version = version;
    }

    private String switchValue(String value) {
        if ("on".equals(value)) {
            return "on";
        }
        return "off";
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getPrivateBucket() {
        return privateBucket;
    }

    public void setPrivateBucket(String privateBucket) {
        this.privateBucket = privateBucket;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSupportHttps() {
        return supportHttps;
    }

    public void setSupportHttps(String supportHttps) {
        this.supportHttps = supportHttps;
    }

    public String getSyncTemplate() {
        return syncTemplate;
    }

    public void setSyncTemplate(String syncTemplate) {
        this.syncTemplate = syncTemplate;
    }

    public String getSyncHtml() {
        return syncHtml;
    }

    public void setSyncHtml(String syncHtml) {
        this.syncHtml = syncHtml;
    }

    public String getCacheMap() {
        return cacheMap;
    }

    public void setCacheMap(String cacheMap) {
        this.cacheMap = cacheMap;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
