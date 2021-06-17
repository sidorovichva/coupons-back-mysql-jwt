package com.vs.couponsbackmysqljwt.security.jwt.romanian;

import com.vs.couponsbackmysqljwt.beans.Company;
import com.vs.couponsbackmysqljwt.beans.Customer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Data
public class UserPrincipal implements UserDetails {
    private final Customer customer;
    private final Company company;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (this.customer != null) authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        if (this.company != null) authorities.add(new SimpleGrantedAuthority("ROLE_COMPANY"));
        return authorities;
    }

    @Override
    public String getPassword() {
        if (this.customer != null) return this.customer.getPassword();
        if (this.company != null) return this.company.getPassword();
        return null;
    }

    @Override
    public String getUsername() {
        if (this.customer != null) return this.customer.getEmail();
        if (this.company != null) return this.company.getEmail();
        return null;
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
