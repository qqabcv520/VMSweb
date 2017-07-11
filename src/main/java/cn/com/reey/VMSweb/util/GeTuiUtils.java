/**
 * filename：GeTuiUtils.java
 *
 * date：2016年7月11日
 * Copyright reey Corporation 2016
 *
 */
package cn.com.reey.VMSweb.util;


import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.NotyPopLoadTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public class GeTuiUtils {
    private String getuiAppId = "";
	private String getuiAppKey = "";
    private String getuiMasterSecret = "";

    public String getGetuiAppId() {
		return getuiAppId;
	}
	public void setGetuiAppId(String getuiAppId) {
		this.getuiAppId = getuiAppId;
	}
	public String getGetuiAppKey() {
		return getuiAppKey;
	}
	public void setGetuiAppKey(String getuiAppKey) {
		this.getuiAppKey = getuiAppKey;
	}
	public String getGetuiMasterSecret() {
		return getuiMasterSecret;
	}
	public void setGetuiMasterSecret(String getuiMasterSecret) {
		this.getuiMasterSecret = getuiMasterSecret;
	}

    /**
     * 发送指定用户推送
     * @author 范子才
     * @param cid 用户设备id
     * @param title 推送标题
     * @param content 内容
     * @throws Exception
     * @version 2016年7月11日 上午11:14:52
     */
    public void sendSingle(String cid, String title, String content, String transmission) {
    	IGtPush push = new IGtPush(getuiAppKey, getuiMasterSecret);
        NotificationTemplate template = notificationTemplateDemo(title, content, transmission);
        SingleMessage message = new SingleMessage();
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 3600 * 1000);
        message.setData(template);
        // 可选，1为wifi，0为不限制网络环境。根据手机处于的网络情况，决定是否下发
        message.setPushNetWorkType(0);
        Target target = new Target();
        target.setAppId(getuiAppId);
        target.setClientId(cid);
        //target.setAlias(Alias);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        if (ret != null) {
            System.out.println(ret.getResponse().toString());
        } else {
            System.out.println("服务器响应异常");
        }
    }
    public NotificationTemplate notificationTemplateDemo(String title, String content, String transmission) {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(getuiAppId);
        template.setAppkey(getuiAppKey);
        // 设置通知栏标题与内容
        template.setTitle(title);
        template.setText(content);
        // 配置通知栏图标
        template.setLogo("push.png");
        // 配置通知栏网络图标
        //template.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        template.setIsRing(true);
        template.setIsVibrate(true);
        template.setIsClearable(true);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        template.setTransmissionContent(transmission);
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
        return template;
    }
}
