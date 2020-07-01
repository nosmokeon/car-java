package com.lyc.carjava.config.shiro;



import com.lyc.carjava.config.shiro.filter.JWTFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootConfiguration
public class ShiroConfig {



    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager)
    {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //配置内置过滤器
        // 拦截所有被配置的接口
        /*
        anon 无需认证就能访问
        authc 必须认证了才能访问
        user 必须使用rememme功能才能使用
        perms 拥有对某个资源的权限才能使用
        role 拥有某个角色的权限
         */
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //配置不被拦截的链接
        filterChainDefinitionMap.put("/sys/login", "anon"); //登录接口排除
        filterChainDefinitionMap.put("/app/login", "anon"); //登录接口排除
        filterChainDefinitionMap.put("/app/register", "anon");
        filterChainDefinitionMap.put("**/doc.html", "anon");
        filterChainDefinitionMap.put("**/*.js", "anon");
        filterChainDefinitionMap.put("**/*.css", "anon");
        filterChainDefinitionMap.put("**/*.html", "anon");
        filterChainDefinitionMap.put("**/*.svg", "anon");
        filterChainDefinitionMap.put("**/*.jpg", "anon");
        filterChainDefinitionMap.put("**/*.png", "anon");
        filterChainDefinitionMap.put("**/*.ico", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger**/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        //添加自己的Filter，
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("jwt",new JWTFilter());
        shiroFilterFactoryBean.setFilters(filters);
        //过滤链定义，从上向下顺序执行，一般将/**放在最为下边
        filterChainDefinitionMap.put("/**", "jwt");
        return shiroFilterFactoryBean;
    }





    @Bean("securityManager")
    public DefaultWebSecurityManager securityManager(UserRealm userRealm)
    {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        /*
		 * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-
		 * StatelessApplications%28Sessionless%29
         */

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);


        return securityManager;
    }



    /**
     * 会话管理类 禁用session
     * @return
     */
    @Bean
    public DefaultSessionManager defaultSessionManager(){
        DefaultSessionManager manager = new DefaultSessionManager();
        manager.setSessionValidationSchedulerEnabled(false);
        return manager;
    }




    /**
     * 下面的代码是添加注解支持
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }




    /**
     * Shiro生命周期处理
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }




    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
