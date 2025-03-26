package sample.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sample.common.util.jwt.JwtAuthorizationFilter;
import sample.common.util.jwt.JwtUtil;
import sample.common.util.oauth2.CustomOAuth2UserService;
import sample.common.util.oauth2.OAuth2LoginSuccessHandler;

@Configuration
@EnableWebSecurity // security 활성화 어노테이션
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler OAuth2LoginSuccessHandler;
    private final JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 방어 기능 비활성화 (jwt 토큰을 사용할 것이기에 필요없음)
                .csrf(AbstractHttpConfigurer::disable)
                // 폼 로그인 비활성화
                // .formLogin(AbstractHttpConfigurer::disable)
                // HTTP Basic 인증 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)
                // oauth2 로그인
                    //  - userInfoEndPoint에서 사용자 정보 불러오고,
                    //  - successHandler에서 로그인 성공 시 JWT 생성 및 반환로직
                .oauth2Login(oauth2 ->
                        oauth2.userInfoEndpoint(userInfoEndpoint ->
                                userInfoEndpoint.userService(customOAuth2UserService)
                        ).successHandler(OAuth2LoginSuccessHandler)
                )
                // 세션 사용하지 않음
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // oauth 로그인 관련, 회원가입, 로그인 체크 - permit all
                        .requestMatchers(
                                "/oauth2/authorization/kakao", // 카카오 로그인 요청
                                "/login/oauth2/code/**") // 카카오 인증 콜백
                        .permitAll()
                        .anyRequest().authenticated() // 허가된 사람만 인가
                )
                // JWTFiler
                .addFilterBefore(new JwtAuthorizationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }


}
