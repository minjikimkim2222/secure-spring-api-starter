package sample.common.util.oauth2;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import sample.common.util.jwt.JwtUtil;
import sample.common.util.oauth2.dto.CustomOAuth2User;
import sample.domain.user.domain.User;

// 카카오 로그인 성공 시, 콜백 핸들러
// 1. JWT 토큰 발급
    // - 이때, JWT payload는 보안상 최소한의 정보(userId, role)만 담겠다
// 2. 클라이언트에게 JWT를 포함해서, 리다이렉트 URL 생성
@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 1. CustomOAuth2UserService에서 설정한 OAuth2User 정보 가져오기
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        User user = customUserDetails.getUser();
        Long userId = customUserDetails.getUserId();
        String email = customUserDetails.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        log.info("user, userId, email, role :: {} {} {} {}", user, userId, email, role);

        // 2. 1)의 사용자 정보를 담아, accessToken과 refreshToken 발행
        // 유효기간 30분
        String accessToken = jwtUtil.createAccessToken("accessToken", userId, role, 30 * 60 * 1000L);
        // 유효기간 30일
        String refreshToken = jwtUtil.createRefreshToken("refreshToken", userId, 30 * 24 * 60 * 60 * 1000L);

    }

}
