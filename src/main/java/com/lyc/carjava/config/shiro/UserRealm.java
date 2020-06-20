package com.lyc.carjava.config.shiro;



import com.lyc.carjava.moudle.base.vo.TestToken;
import com.lyc.carjava.util.JWTUtil;
import com.lyc.carjava.util.RedisUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    RedisUtil redisUtil;


    /**
     * 必须重写此方法，不然Shiro会报错
     * 确保认证成功，token的类
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof TestToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("用户授权");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("visit");
        return info;
    }

    //认证
    @SneakyThrows
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("用户认证");
        String token = (String) authenticationToken.getPrincipal();
        if(token == null)
        {
            return null;
        }
        if(JWTUtil.verify(token,JWTUtil.secret))
        {
            if(!JWTUtil.isOverdue(token))
            {
//                throw new BizException("Token失效，请重新登录");
                throw new AuthenticationException("token runtime");
            }
            if( redisUtil.get(JWTUtil.getUserName(token)) == null)
            {
//                throw new BizException("Token失效，请重新登录");
                throw new AuthenticationException("wrong token");
            }
            return  new SimpleAuthenticationInfo("",authenticationToken.getPrincipal(), getName());
        }
        else
        {
//            throw new BizException("Token失效，请重新登录");
            throw new AuthenticationException("wrong token");
        }

    }


}
