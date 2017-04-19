package com.example.goals.listviewsignalandmultiplechoice.rxBus;

/**
 * Created by huyongqiang on 2017/4/17.
 * <p>
 * 自定义时间类型
 */

public class EventRxBus {
    private int eventCode;
    private String eventMsg;

    public EventRxBus(int eventCode, String eventMsg) {
        this.eventCode = eventCode;
        this.eventMsg = eventMsg;
    }

    public int getEventCode() {
        return eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventMsg() {
        return eventMsg;
    }

    public void setEventMsg(String eventMsg) {
        this.eventMsg = eventMsg;
    }
}
