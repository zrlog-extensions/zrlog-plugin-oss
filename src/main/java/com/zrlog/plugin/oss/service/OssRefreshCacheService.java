package com.zrlog.plugin.oss.service;

import com.zrlog.plugin.IOSession;
import com.zrlog.plugin.api.Capability;
import com.zrlog.plugin.api.IPluginService;
import com.zrlog.plugin.api.Service;
import com.zrlog.plugin.data.codec.MsgPacket;

@Service("oss.refreshCache")
@Capability(key = "oss.refreshCache", type = "event_handler", label = "刷新阿里云存储缓存",
        description = "响应系统缓存刷新事件，同步模板和静态缓存文件。",
        exposure = {"runtime_event"}, riskLevel = "medium", timeoutSeconds = 300, channel = "system.refreshCache")
public class OssRefreshCacheService implements IPluginService {
    @Override public void handle(IOSession session, MsgPacket packet) {
        new OssStaticSyncService().handle(session, packet);
    }
}
