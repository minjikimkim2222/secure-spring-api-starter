package sample.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(createAntMatchers(allowUrls)).permitAll() // 허용 URL 설정
                                .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable) // CSRF 방어 기능 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // 폼 로그인 비활성화
                .httpBasic(AbstractHttpConfigurer::disable); // HTTP Basic 인증 비활성화

        return http.build();
    }

    // 인증 필요없이 항상 허용해주는 url들
    public static final String[] allowUrls = {
            "/login",
            "/auth/login/kakao/**" // oauth 콜백 url
    };

    // AntPathRequestMatcher 배열을 생성하는 helper method (/**, /1, 같은 확장성 있는 경로 매칭을 위해)
    private AntPathRequestMatcher[] createAntMatchers(String[] urls){
        return java.util.Arrays.stream(urls)
                .map(AntPathRequestMatcher::new)
                .toArray(AntPathRequestMatcher[]::new);
    }

}
