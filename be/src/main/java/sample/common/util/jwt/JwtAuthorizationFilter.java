package sample.common.util.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import sample.common.exception.ErrorMessage;
import sample.common.exception.UnauthorizedException;

// JWT 검증 필터
// 1. 헤더에서 JWT 추출, 2. 토큰 검증, 3. 유효하면 사용자정보를 SecurityContextHolder에 세팅
// 그러면, 이후 컨트롤러에서 @AuthenticationPrincipal에서 저장했던 사용자 정보를 꺼내쓸 수 있음
@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        Optional<String> accessTokenOptional = findAccessToken(request, "accessToken");

        log.info("JwtAuthorizationFilter에서 받은 accessTokenOptional :: {}", accessTokenOptional);

        // 토큰이 없다면, 401 에러 발생 (로그인하지 않은 사용자)
        if (accessTokenOptional.isEmpty()){
            log.error("요청 경로 : " + request.getRequestURI() + " / 쿠키 없음");
            throw new UnauthorizedException(ErrorMessage.COOKIE_NOT_EXIST);
        }

        // accessToken 추출
        String accessToken = accessTokenOptional.get();
        log.info("accessToken :: {}", accessToken);

    }

    // 쿠키에서 토큰 찾기
    private Optional<String> findAccessToken(HttpServletRequest request, String name){
        Cookie[] cookies = request.getCookies();

        return cookies == null ? Optional.empty() : Arrays.stream(cookies)
                .filter(cookie -> name.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue);
    }

}
