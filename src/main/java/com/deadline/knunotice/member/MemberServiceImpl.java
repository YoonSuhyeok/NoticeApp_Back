package com.deadline.knunotice.member;

import com.deadline.knunotice.config.authentication.AuthenticationProviderService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.function.Supplier;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    private final MemberRepository memberRepository;

    private final AuthenticationProviderService authenticationProviderService;

    public MemberServiceImpl(MemberRepository memberRepository, AuthenticationProviderService authenticationProviderService) {
        this.memberRepository = memberRepository;
        this.authenticationProviderService = authenticationProviderService;
    }

    @Override
    public Member findMemberByUsername(String username) {
        Supplier<UsernameNotFoundException> s =
                () -> new UsernameNotFoundException(
                        "Problem during authentication!");

        return memberRepository.findMemberByUsername(username).orElseThrow(s);
    }

    @Override
    public Member login(String username, String password) {
        Supplier<UsernameNotFoundException> s =
                () -> new UsernameNotFoundException(
                        "Problem during authentication!");

        Member member = memberRepository.findMemberByUsername(username).orElseThrow(s);

        MemberAuthentication memberAuthentication = new MemberAuthentication(member);
        // 로직을 통해서 인증 진행
        authenticationProviderService.authenticate(memberAuthentication);
        // token 발급해야해!
        return member;
    }

    @Override
    public Member signUp(String email) {

        Member member = memberRepository.findMemberByEmail(email).orElse(null);

        if(member == null) {
            member = new Member();
            member.setEmail(email);
            member = memberRepository.save(member);
        }

        return member;
    }

    @Override
    public MemberAuthentication signUp(SignUpDTO signUpDTO) {

        Member member = new Member(signUpDTO);

        return new MemberAuthentication(member);
    }

    @Override
    public Member findMemberByEmail(String email) {
        return memberRepository.findMemberByEmail(email).orElse(null);
    }

    public void save(TokenRequestDTO idTokenString) throws GeneralSecurityException, IOException {
        //// Specify the CLIENT_ID of the app that accesses the backend:
        // Or, if multiple clients access the backend:
        //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                .build();

        // (Receive idTokenString by HTTPS POST)

        GoogleIdToken idToken = verifier.verify(idTokenString.getIdToken());
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            // Use or store profile information
            // ...

        } else {
            System.out.println("Invalid ID token.");
        }
    }
}
