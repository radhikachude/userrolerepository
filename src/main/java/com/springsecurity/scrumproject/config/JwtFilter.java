package com.springsecurity.scrumproject.config;

import com.springsecurity.scrumproject.service.JWTService;
import com.springsecurity.scrumproject.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService  jwtService;
    @Autowired
    MyUserDetailsService myUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token=null;
        String username= null;

        if(authHeader!=null && authHeader.startsWith("Bearer"))
        {
            token= authHeader.substring(7);
            username= jwtService.extractUserName(token);
        }
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
          /*UserDetails userDetails;
            userDetails = context.getBean(MyUserDetailsService.class)
                    .loadUserByUsername(username);*/
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
            if(jwtService.validateToken(token,userDetails))
              {
                  UsernamePasswordAuthenticationToken authtoken=
                          new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
       authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
       SecurityContextHolder.getContext().setAuthentication(authtoken);
              }
        }
        filterChain.doFilter(request,response);
    }
}