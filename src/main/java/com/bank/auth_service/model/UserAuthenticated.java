package com.bank.auth_service.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Implementation of {@link UserDetails} and {@link CredentialsContainer} for authenticated users.
 * Used by Spring Security to represent the authenticated user's details and authorities.
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0.0, 06/23/2025
 * @since 1.0.0
 */
public class UserAuthenticated implements UserDetails, CredentialsContainer {
    
        private final User user;
    
        public UserAuthenticated(User user) {
            this.user = user;
        }
    
        @Override
        public String getUsername() {
            return user.getEmail();
        }
    
        @Override
        public String getPassword() {
            return user.getPassword();
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
    
        @Override
        public void eraseCredentials() {
            user.setPassword(null);
        }

        public User getUser(){
            return user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority("ROLE_" + user.getUserRole().name()));
        }

}
