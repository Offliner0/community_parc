package com.example.community_parc.jwt;

import com.example.community_parc.domain.Member;
import com.example.community_parc.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter {//extends OncePerRequestFilter {

//    private final JwtUtil jwtUtil;
//
//    private final MemberService memberService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        //http 헤더에서 Authorization 값을 가져옴
//        final String authHeader = request.getHeader("Authorization");
//
//        //Authorization 헤더가 ㅇ없거나 bearer토큰이 아닌 경우 다음 필터로 진행
//        if(authHeader != null && authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        //bearer 제거하고 실제 jwt 토큰만 추출
//        String jwt = authHeader.substring(7);
//
//        //jwt 토큰에서 사용자명 추출
//        String username = jwtUtil.extractUsername(jwt);
//
//        // 사용자명이 존재하고 현재 SecurityContext에 인증 정보가 없는 경우
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            // DB에서 사용자 정보를 가져옴
//            Member member = memberService.getMemberById(username);
//
//            // 토큰이 유효한 경우
//            if (jwtUtil.validateToken(jwt)) {
//                // 인증 토큰 생성
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        member,  // 사용자 정보
//                        null,        // 자격증명(보통 비밀번호지만 JWT에서는 불필요)
//                        member.getAuthorities()  // 사용자 권한 정보 ??
//                );
//                // 현재 요청 정보를 인증 토큰에 설정
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                // SecurityContext에 인증 정보 설정
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//        // 다음 필터로 진행
//        filterChain.doFilter(request, response);




}
