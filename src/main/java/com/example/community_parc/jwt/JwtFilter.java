package com.example.community_parc.jwt;


import com.example.community_parc.domain.Member;
import com.example.community_parc.domain.UserRole;
import com.example.community_parc.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
//토큰검증
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //헤더
        String authorizationHeader = request.getHeader("Authorization");

        //헤더가 null이 아니고 Bearer로 시작
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        //"Bearer " 다음 값
        String token = authorizationHeader.split(" ")[1];

        //토큰 만료 검증
        if(jwtUtil.isTokenExpired(token)) {

            System.out.println("token expired");
            filterChain.doFilter(request, response);

            return;
        }

        String email = jwtUtil.getEmail(token);
        String role = jwtUtil.getRole(token);

        Member member = new Member();
        member.setEmail(email);
        member.setPassword("tempassword");
        member.setRole(UserRole.valueOf(role));

        CustomUserDetails customUserDetails = new CustomUserDetails(member);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        //요청이 끝날 때 까지만 지속되는 임시 세션
        SecurityContextHolder.getContext().setAuthentication(authToken);

        //다음 필터로 전달
        filterChain.doFilter(request, response);


    }
}
