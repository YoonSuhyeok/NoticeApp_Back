package com.deadline.knunotice.config.username;

import com.deadline.knunotice.member.Member;
import com.deadline.knunotice.member.MemberRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class JpaUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public JpaUserDetailService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        Supplier<UsernameNotFoundException> s =
                () -> new UsernameNotFoundException(
                        "Problem during authentication!");

        Member member = memberRepository.findMemberByUsername(username).orElseThrow(s);

        return new CustomUserDetails(member);
    }

}
