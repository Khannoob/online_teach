package com.sysu.guli.service.ucenter.service;

public interface WXService {
    String WXLogin(String state);

    String getToken(String code, String state);
}
