package sample.domain.token.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;

}
