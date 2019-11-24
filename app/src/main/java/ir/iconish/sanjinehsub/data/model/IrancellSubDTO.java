package ir.iconish.sanjinehsub.data.model;

import java.io.Serializable;


public class IrancellSubDTO implements Serializable {

    private Integer appId;
    private String versionName;
    private String versionCode;
    private Integer channelId;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString() {
        return "IrancellSubDTO{" +
                "appId=" + appId +
                ", versionName='" + versionName + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ", channelId=" + channelId +
                '}';
    }
}
