package sample.common.util.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    // OAuth2 제공자(카카오)로부터 제공받은 사용자 정보를, 우리 서비스에 맞게 가공 / 변환
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("CustomOAuth2UserService :: {}", oAuth2User);
        log.info("oAuthUser.getAttributes :: {}", oAuth2User.getAttributes());

        return super.loadUser(userRequest);
    }

}
