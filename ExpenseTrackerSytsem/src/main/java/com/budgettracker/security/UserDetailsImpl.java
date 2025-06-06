package com.budgettracker.security;

import com.budgettracker.model.User;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

	@Autowired
    private  User user;

    public UserDetailsImpl(User user2) {
		// TODO Auto-generated constructor stub
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // no roles implemented
    }

    @Override
    public String getPassword() { return user.getPassword(); }

    @Override
    public String getUsername() { return user.getUsername(); }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    public Long getId() { return user.getId(); }

    public String getEmail() { return user.getEmail(); }
}
