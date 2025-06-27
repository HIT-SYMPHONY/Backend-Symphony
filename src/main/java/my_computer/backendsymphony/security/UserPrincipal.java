package my_computer.backendsymphony.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import my_computer.backendsymphony.domain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@EqualsAndHashCode(of = "id")
public class UserPrincipal implements UserDetails {

  private final String id;

  // studentCode dùng để đăng nhập
  private final String studentCode;

  // displayName là tên hiển thị (không dùng để login)
  // ko đặt là username để tránh phương thức mặc định là GetUsername
  private final String displayName;

  @JsonIgnore
  private final String password;

  private final Collection<? extends GrantedAuthority> authorities;

  public UserPrincipal(String id, String studentCode, String displayName, String password,
                       Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.studentCode = studentCode;
    this.displayName = displayName;
    this.password = password;
    this.authorities = authorities == null ? null : new ArrayList<>(authorities);
  }

  public static UserPrincipal create(User user) {
    List<GrantedAuthority> authorities = List.of(
            new SimpleGrantedAuthority(user.getRole().name())
    );
    return new UserPrincipal(
            user.getId(),
            user.getStudentCode(),
            user.getUsername(), // Username thực tế là display name
            user.getPassword(),
            authorities
    );
  }

  // Override vì Spring Security mặc định dùng username là định danh login
  @Override
  public String getUsername() {
    return studentCode;
  }

  @Override public String getPassword() { return password; }

  @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

  @Override public boolean isAccountNonExpired() { return true; }

  @Override public boolean isAccountNonLocked() { return true; }

  @Override public boolean isCredentialsNonExpired() { return true; }

  @Override public boolean isEnabled() { return true; }
}
