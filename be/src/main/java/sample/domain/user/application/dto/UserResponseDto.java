package sample.domain.user.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    private Long userId;
    private String role;
}
