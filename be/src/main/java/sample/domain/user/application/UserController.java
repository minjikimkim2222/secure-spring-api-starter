package sample.domain.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.common.util.oauth2.dto.CustomOAuth2User;
import sample.domain.user.application.dto.UserResponseDto;
import sample.domain.user.domain.User;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    // 로그인된 유저 정보를 확인하는 api
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyInfo(@AuthenticationPrincipal CustomOAuth2User customOAuth2User){
        User user = customOAuth2User.getUser();

        UserResponseDto userResponseDto = UserResponseDto.builder()
                .userId(user.getId())
                .role(user.getRole().getName())
                .build();
        return ResponseEntity.ok(userResponseDto);
    }

}
