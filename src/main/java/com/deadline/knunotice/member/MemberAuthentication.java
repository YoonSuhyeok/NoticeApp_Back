package com.deadline.knunotice.member;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Collection;

public class MemberAuthentication implements Authentication {

    private final Member member;

    private boolean authenticated = false;

    public MemberAuthentication(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return member.getPassword();
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Member getPrincipal() {
        return member;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return member.getUsername();
    }

    public void setDetails(WebAuthenticationDetails buildDetails) {
    }

    public String getEmail() { return member.getEmail(); }

    public Member getMember() { return member; }

}
