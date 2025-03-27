package sample.common.util.oauth2.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import sample.domain.user.domain.User;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    private final User user;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    // 사용자가 가지는 권한 설정
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole().getName(); // 유저의 권한 리턴
            }
        });

        return authorities;
    }

    @Override
    public String getName() {
        return user.getEmail();
    }

    public User getUser(){
        return user;
    }

    public Long getUserId(){
        return user.getUserId();
    }

    public String getNickname(){
        return user.getNickname();
    }

}
