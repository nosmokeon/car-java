package com.lyc.carjava.resolver;


import com.lyc.carjava.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@Slf4j
public class CurrentUserResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        UserInfo reqUser = new UserInfo();
        String requestTonken = null;
        try {
            String token = webRequest.getHeader("token");
//            requestTonken = AesEncryptUtil.desEncrypt(token).trim();
//            String userId = requestTonken.split("_")[1];
            String userId = JWTUtil.getUserName(token);
            reqUser.setUserId(userId);
        } catch (Exception e) {
            log.error("用户解析失败:{}", requestTonken);
        }
        return reqUser;

    }
}
