package com.wf.apiwf.entity;

import com.wf.apiwf.security.SystemUserRoles;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class SystemUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String jwt;

    @NotEmpty
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    @Size(min = 4, max = 8)
    private String username;

    @NotEmpty
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private SystemUserRoles systemUserRoles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(systemUserRoles.name());
        return Collections.singleton(grantedAuthority);
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

    public SystemUser(String jwt) {this.jwt = jwt;}

    public SystemUser(String name, String email, String username, String password, SystemUserRoles systemUserRoles) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.systemUserRoles = systemUserRoles;
    }
}
