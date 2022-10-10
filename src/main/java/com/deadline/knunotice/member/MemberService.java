package com.deadline.knunotice.member;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface MemberService {

    Member findMemberByUsername(String username);

    Member login(String username, String password);

    Member signUp(String email);

    MemberAuthentication signUp(SignUpDTO signUpDTO);

    Member findMemberByEmail(String email);

    Member save(TokenRequestDTO idToken) throws GeneralSecurityException, IOException;

}
