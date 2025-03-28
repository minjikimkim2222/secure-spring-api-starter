package sample.domain.token.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.common.util.jwt.JwtUtil;
import sample.domain.token.application.dto.AuthenticationResponse;
import sample.domain.token.application.dto.RefreshTokenRequest;
import sample.domain.token.presentation.TokenService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/refresh-token")
@Slf4j
public class TokenController {
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;
    @PostMapping
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request){
        String refreshToken = request.getRefreshToken();

        log.info("일로 오긴 했니?");
        // 리프레시 토큰 검증
        Long userId = jwtUtil.getUserId(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        // 리프레시 토큰 유효성 검증
        if (tokenService.validateToken(refreshToken, userId)){
            // 유효한 토큰이라면, 새로운 엑세스 토큰 생성
            String newAccessToken = jwtUtil.createAccessToken("accessToken", userId, role, 30 * 60 * 1000L);
            log.info("유효한 토큰이라 엑세스 토큰 생성했어요... ");
            AuthenticationResponse authenticationResponse = new AuthenticationResponse(newAccessToken, refreshToken);
            return ResponseEntity.ok().body(authenticationResponse);

        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
    }
}
