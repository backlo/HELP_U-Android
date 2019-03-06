package com.example.help_u.Provider.Data;

public class SendAlarmSet {

    private String id;
    private boolean alarm;

    public SendAlarmSet(String id, boolean alarm) {
        this.id = id;
        this.alarm = alarm;
    }

    public SendAlarmSet(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }
}
