package com.zrlog.plugin.oss.controller;

import com.google.gson.Gson;
import com.zrlog.plugin.IOSession;
import com.zrlog.plugin.common.IdUtil;
import com.zrlog.plugin.data.codec.ContentType;
import com.zrlog.plugin.data.codec.HttpRequestInfo;
import com.zrlog.plugin.data.codec.MsgPacket;
import com.zrlog.plugin.data.codec.MsgPacketStatus;
import com.zrlog.plugin.oss.service.OssStorageConfig;
import com.zrlog.plugin.type.ActionType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaochun on 2016/2/13.
 */
public class OssController {

    private static final String CONFIG_KEYS = "access_key,host,secret_key,private_bucket,bucket,syncTemplate,appId,region,supportHttps,syncHtml";

    private final IOSession session;
    private final MsgPacket requestPacket;
    private final HttpRequestInfo requestInfo;
    private final Gson gson = new Gson();

    public OssController(IOSession session, MsgPacket requestPacket, HttpRequestInfo requestInfo) {
        this.session = session;
        this.requestPacket = requestPacket;
        this.requestInfo = requestInfo;
    }

    public void update() {
        session.sendMsg(new MsgPacket(requestConfig(), ContentType.JSON, MsgPacketStatus.SEND_REQUEST, IdUtil.getInt(), ActionType.SET_WEBSITE.name()), msgPacket -> {
            session.sendMsg(new MsgPacket(StorageApiResponse.success(), ContentType.JSON, MsgPacketStatus.RESPONSE_SUCCESS, requestPacket.getMsgId(), requestPacket.getMethodStr()));
        });
    }

    public void index() {
        Map<String, Object> data = new HashMap<>();
        data.put("theme", isDarkMode() ? "dark" : "light");
        data.put("data", gson.toJson(StorageApiResponse.success(pageData())));
        session.responseHtml("/templates/index", data, requestPacket.getMethodStr(), requestPacket.getMsgId());
    }

    public void json() {
        response(StorageApiResponse.success(pageData()));
    }

    public void info() {
        response(loadConfig());
    }

    private StorageInfoResponse<OssStorageConfig> pageData() {
        StorageInfoResponse<OssStorageConfig> data = new StorageInfoResponse<OssStorageConfig>();
        data.setDark(isDarkMode());
        data.setColorPrimary(getAdminColorPrimary());
        data.setPlugin(session.getPlugin());
        data.setProvider(provider());
        data.setConfig(loadConfig());
        return data;
    }

    private StorageProvider provider() {
        return new StorageProvider("oss", "阿里云存储设置", "https://blog.zrlog.com/oss-install.html",
                "Endpoint（地域节点）", true, false, true, true);
    }

    private OssStorageConfig loadConfig() {
        OssStorageConfig config = session.getResponseSync(ContentType.JSON, WebsiteKeyRequest.of(CONFIG_KEYS), ActionType.GET_WEBSITE, OssStorageConfig.class);
        if (config == null) {
            config = new OssStorageConfig();
        }
        config.normalizeForPage(session.getPlugin().getVersion());
        return config;
    }

    private OssStorageConfig requestConfig() {
        OssStorageConfig config = new OssStorageConfig();
        config.setAccessKey(paramValue("access_key"));
        config.setSecretKey(paramValue("secret_key"));
        config.setHost(paramValue("host"));
        config.setBucket(paramValue("bucket"));
        config.setPrivateBucket(paramValue("private_bucket"));
        config.setRegion(paramValue("region"));
        config.setSupportHttps(paramValue("supportHttps"));
        config.setSyncTemplate(paramValue("syncTemplate"));
        config.setSyncHtml(paramValue("syncHtml"));
        return config;
    }

    private void response(Object data) {
        session.sendMsg(ContentType.JSON, data, requestPacket.getMethodStr(), requestPacket.getMsgId(), MsgPacketStatus.RESPONSE_SUCCESS);
    }

    private String paramValue(String key) {
        if (requestInfo.getParam() == null || requestInfo.getParam().get(key) == null || requestInfo.getParam().get(key).length == 0) {
            return "";
        }
        return requestInfo.getParam().get(key)[0];
    }

    private boolean isDarkMode() {
        return requestInfo.isDarkMode();
    }

    private String getAdminColorPrimary() {
        return requestInfo.getAdminColorPrimary();
    }
}
