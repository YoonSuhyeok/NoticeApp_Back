package com.deadline.knunotice.config.authentication;

import com.deadline.knunotice.config.username.CustomUserDetails;
import com.deadline.knunotice.config.username.JpaUserDetailService;
import com.deadline.knunotice.member.EncryptionAlgorithm;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.deadline.knunotice.member.EncryptionAlgorithm.BCRYPT;
import static com.deadline.knunotice.member.EncryptionAlgorithm.SCRYPT;

@Service
public class AuthenticationProviderService implements AuthenticationProvider {

    private final JpaUserDetailService userDetailService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final SCryptPasswordEncoder sCryptPasswordEncoder;

    public AuthenticationProviderService(JpaUserDetailService userDetailService, BCryptPasswordEncoder bCryptPasswordEncoder, SCryptPasswordEncoder sCryptPasswordEncoder) {
        this.userDetailService = userDetailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.sCryptPasswordEncoder = sCryptPasswordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        CustomUserDetails user = userDetailService.loadUserByUsername(username);
        EncryptionAlgorithm userAlgorithm = user.getUser().getAlgorithm();

        if(userAlgorithm == BCRYPT) {
            return checkPassword(user, password, bCryptPasswordEncoder);
        } else if(userAlgorithm == SCRYPT){
            return checkPassword(user, password, sCryptPasswordEncoder);
        }

        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private Authentication checkPassword(CustomUserDetails user, String rawPassword, PasswordEncoder encoder) {
        if(encoder.matches(rawPassword, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        } else {
            throw new BadCredentialsException("Bed credentials");
        }
    }
}
