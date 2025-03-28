package sample.domain.token.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import sample.domain.token.domain.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByUserId(Long userId);
}
