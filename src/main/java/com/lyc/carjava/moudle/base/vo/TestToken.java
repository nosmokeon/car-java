package com.lyc.carjava.moudle.base.vo;

import org.apache.shiro.authc.AuthenticationToken;

public class TestToken implements AuthenticationToken {
    private static final long serialVersionUID = 1L;
    private String token;

    public TestToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
