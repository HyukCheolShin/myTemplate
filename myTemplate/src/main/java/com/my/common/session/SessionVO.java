package com.my.common.session;

import java.io.Serializable;
import java.util.Map;

public class SessionVO implements Serializable{

    private static final long serialVersionUID = 1L;

    private String userId;
    private String userNm;

    public SessionVO(Map<String, String> loginMap) {
        this.userId = loginMap.get("userId");
        this.userNm = loginMap.get("userNm");
    }

    public void changeSessionVO(Map<String, String> loginMap) {
            this.userId = loginMap.get("userId");
            this.userNm = loginMap.get("userNm");
    }

    public String getUserId() {
        return userId;
    }
    public String getUserNm() {
        return userNm;
    }

    @Override
    public String toString() {
        return "userId=" + userId + ","
             + "userNm=" + userNm;
    }
}