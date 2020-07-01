package com.lyc.carjava.config.shiro.filter;

import com.alibaba.fastjson.JSONObject;

import com.lyc.carjava.advice.exceptions.BizException;
import com.lyc.carjava.moudle.base.vo.Result;
import com.lyc.carjava.moudle.base.vo.TestToken;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class JWTFilter extends BasicHttpAuthenticationFilter {

    /**
     * 执行登录认证
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @SneakyThrows
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            executeLogin(request, response);
            return true;
        } catch (Exception e) {
            String message = JSONObject.toJSONString(Result.ERROR(e.getMessage()));
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().println(message);
            response.getWriter().close();
        }
        return false;
    }

    /**
     *
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws BizException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = httpServletRequest.getHeader("token");
        if(token == null)
        {
            throw new BizException("null token");
        }
        //用户凭据
        TestToken testToken = new TestToken(token);
        //执行登录
        Subject subject = SecurityUtils.getSubject();
        try
        {
            subject.login(testToken);
        }
        catch (Exception e)
        {
            throw new BizException(e.getMessage());
        }
        return true;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

}
