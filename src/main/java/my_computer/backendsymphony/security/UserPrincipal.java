package my_computer.backendsymphony.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import my_computer.backendsymphony.domain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@EqualsAndHashCode(of = "id")
public class UserPrincipal implements UserDetails {

  @Getter
  private final String id;

  @Getter
  private final String firstName;

  @Getter
  private final String lastName;

  @JsonIgnore
  private final String username;

  @JsonIgnore
  private String password;

  private final Collection<? extends GrantedAuthority> authorities;

  public UserPrincipal(String studentCode, String password, Collection<? extends GrantedAuthority> authorities) {
    this(null, null, null, studentCode, password, authorities);
  }

  public UserPrincipal(String id, String firstName, String lastName, String studentCode, String password,
                       Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = studentCode;
    this.password = password;

    if (authorities == null) {
      this.authorities = null;
    } else {
      this.authorities = new ArrayList<>(authorities);
    }
  }

  public static UserPrincipal create(User user) {
    List<GrantedAuthority> authorities = new LinkedList<>();
    authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
    return new UserPrincipal(user.getId(), user.getFirstName(), user.getLastName(),
        user.getStudentCode(), user.getPassword(), authorities);
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
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
