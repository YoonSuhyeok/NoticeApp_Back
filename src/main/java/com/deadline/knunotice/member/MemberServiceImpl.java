package com.deadline.knunotice.member;

import com.deadline.knunotice.config.authentication.AuthenticationProviderService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.function.Supplier;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

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

}
