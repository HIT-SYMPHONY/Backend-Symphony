package my_computer.backendsymphony.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import my_computer.backendsymphony.domain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@EqualsAndHashCode(of = "id")
public class UserPrincipal implements UserDetails {

  private final String id;

  /**
   * The unique identifier for the security principal, required by the UserDetails interface.
   * In this application, this field holds the user's unique 'studentCode'.
   */
  private final String username;
  private final String password;
  private final Collection<? extends GrantedAuthority> authorities;

  private UserPrincipal(String id, String studentCode, String password, Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.username = studentCode; // Assign studentCode to the 'username' field
    this.password = password;
    this.authorities = authorities;
  }

  public static UserPrincipal create(User user) {
    List<GrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority(user.getRole().name())
    );
    return new UserPrincipal(
            user.getId(),
            user.getStudentCode(),
            user.getPassword(),
            authorities
    );
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}