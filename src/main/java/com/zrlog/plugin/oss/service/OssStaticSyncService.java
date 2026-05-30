package com.zrlog.plugin.oss.service;

import com.zrlog.plugin.IOSession;
import com.zrlog.plugin.api.IPluginService;
import com.zrlog.plugin.api.ScheduledCapability;
import com.zrlog.plugin.api.Service;
import com.zrlog.plugin.data.codec.MsgPacket;
import com.zrlog.plugin.data.codec.MsgPacketStatus;
import com.zrlog.plugin.message.CapabilityInvokeResult;
import com.zrlog.plugin.oss.timer.SyncTemplateStaticResourceRunnable;

import java.util.HashMap;
import java.util.Map;

@Service("oss.syncStaticResources")
@ScheduledCapability(
        key = "oss.syncStaticResources",
        label = "同步 OSS 静态资源",
        description = "同步模板静态资源和静态缓存文件到阿里云 OSS。",
        defaultCron = "*/5 * * * *",
        timeoutSeconds = 300
)
public class OssStaticSyncService implements IPluginService {

    @Override
    public void handle(IOSession session, MsgPacket msgPacket) {
        CapabilityInvokeResult result = new CapabilityInvokeResult();
        Map<String, Object> data = new HashMap<>();
        try {
            new SyncTemplateStaticResourceRunnable(session).run();
            result.setSuccess(true);
            data.put("message", "OSS static resources sync completed");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage(e.getMessage());
            data.put("message", e.getMessage());
        }
        result.setData(data);
        session.sendJsonMsg(result, msgPacket.getMethodStr(), msgPacket.getMsgId(),
                result.isSuccess() ? MsgPacketStatus.RESPONSE_SUCCESS : MsgPacketStatus.RESPONSE_ERROR);
    }
}
