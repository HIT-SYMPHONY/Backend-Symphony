package my_computer.backendsymphony.domain.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import my_computer.backendsymphony.constant.CommonConstant;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponse {
    String tokenType = CommonConstant.BEARER_TOKEN;
    String accessToken;
    String refreshToken;
    String id;
    Collection<? extends GrantedAuthority> authorities;

    public LoginResponse(String accessToken, String refreshToken, String id, Collection<? extends GrantedAuthority> authorities) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.authorities = authorities;
    }
}
