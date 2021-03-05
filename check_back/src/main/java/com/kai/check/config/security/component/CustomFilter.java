package com.kai.check.config.security.component;

import com.kai.check.pojo.RoleUrl;
import com.kai.check.service.IRoleUrlService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {

    @Resource
    private IRoleUrlService roleUrlService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        //System.out.println("CustomFilter==requestUrl==>" + requestUrl);
        List<RoleUrl> roleUrls = roleUrlService.list();
        for (RoleUrl roleUrl : roleUrls) {
            if (antPathMatcher.match(roleUrl.getRoleUrl(), requestUrl)) {
                String roleName = roleUrl.getRoleName();
                //System.out.println("CustomFilter==roleName==>" + roleName);
                return SecurityConfig.createList(roleName);
            }
        }
        return SecurityConfig.createList("用户");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
