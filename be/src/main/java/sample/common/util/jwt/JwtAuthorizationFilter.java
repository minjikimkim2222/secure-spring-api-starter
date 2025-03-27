package sample.common.util.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

// JWT 검증 필터
// 1. 헤더에서 JWT 추출, 2. 토큰 검증, 3. 유효하면 사용자정보를 SecurityContextHolder에 세팅
// 그러면, 이후 컨트롤러에서 @AuthenticationPrincipal에서 저장했던 사용자 정보를 꺼내쓸 수 있음
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

    }

}
