package com.kai.check.config.security.component;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//jwt的登录授权过滤器
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头内容
        String authHeader = httpServletRequest.getHeader(tokenHeader);
        // 存在token
        if (null != authHeader && authHeader.startsWith(tokenHead)) {
            String authToken = authHeader.substring(tokenHead.length());
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            // token存在但是未登录
            System.out.println(username);
            if (null != username && null == SecurityContextHolder.getContext().getAuthentication()) {
                System.out.println("null == SecurityContextHolder.getContext().getAuthentication()");
                // 登录
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // 验证token是否有效,重新设置用户对象
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    System.out.println("token 有效");
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
