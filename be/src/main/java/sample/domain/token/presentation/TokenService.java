package sample.domain.token.presentation;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sample.common.util.jwt.JwtUtil;
import sample.domain.token.domain.Token;
import sample.domain.token.repository.TokenRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    private final TokenRepository tokenRepository;
    private final JwtUtil jwtUtil;

    public Boolean validateToken(String token, Long userId){
        // DB에서 해당 userId와 일치하는 리프레시토큰을 찾는다.
        Optional<Token> savedToken = tokenRepository.findByUserId(userId);
        
        // DB에서 userId에 대응되는 리프레시토큰 없으면, 유효하지 않음
        if (savedToken.isEmpty()){
            log.info("여기에 걸렸니 ? -- 1 ");
            return false;
        }
        
        // 리프레시 토큰이 DB에 저장된 토큰과 일치하는지 확인
        if (!savedToken.get().getRefreshToken().equals(token)){
            log.info("여기에 걸렸니 ? -- 2 ");
            return false;
        }
        
        // 리프레시 토큰의 만료여부 확인
        if(jwtUtil.isExpired(token)){
            log.info("여기에 걸렸니 ? -- 3 ");
            return false; // 만료된 토큰은 유효하지 않음
        }

        log.info("여기에 걸렸니 ? -- 4 ");
        return true; // 모든 조건 만족 시, 유효한 토큰
    }
}
