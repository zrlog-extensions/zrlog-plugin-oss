package com.zrlog.plugin.oss.handler;

import com.zrlog.plugin.IOSession;
import com.zrlog.plugin.api.IConnectHandler;
import com.zrlog.plugin.data.codec.MsgPacket;
import com.zrlog.plugin.oss.timer.SyncTemplateStaticResourceRunnable;

public class ConnectHandler implements IConnectHandler {

    private SyncTemplateStaticResourceRunnable syncTemplateStaticResourceRunnable;

    @Override
    public void handler(IOSession ioSession, MsgPacket msgPacket) {
        this.syncTemplateStaticResourceRunnable = new SyncTemplateStaticResourceRunnable(ioSession);
    }

    public SyncTemplateStaticResourceRunnable getSyncTemplateStaticResourceRunnable() {
        return syncTemplateStaticResourceRunnable;
    }
}
