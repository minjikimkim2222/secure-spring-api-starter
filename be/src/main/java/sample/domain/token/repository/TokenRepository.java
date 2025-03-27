package sample.domain.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.domain.token.domain.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {

}
