package com.deadline.knunotice.member;

import com.deadline.knunotice.config.jsonwebtoken.JwtTokenProviderService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final JwtTokenProviderService tokenProvider;

    private final MemberService memberService;

    public MemberController(JwtTokenProviderService tokenProvider, MemberService memberService) {
        this.tokenProvider = tokenProvider;
        this.memberService = memberService;
    }

    @GetMapping("")
    public ResponseEntity<Member> findMemberByUsername(@Param("username") String username) {
        Member member = memberService.findMemberByUsername(username);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<String> generateRefreshToken(@RequestParam("email") String email, @RequestParam("refreshToken") String refreshToken) {
        if(!tokenProvider.validateToken(refreshToken, 1)) {
            return new ResponseEntity<>("Refresh Token is not Validate", HttpStatus.FORBIDDEN);
        }
        Member member = memberService.findMemberByEmail(email);
        MemberAuthentication memberAuthentication = new MemberAuthentication(member);
        String newRefreshToken = tokenProvider.generateToken(memberAuthentication, 1);

        return new ResponseEntity<>(newRefreshToken, HttpStatus.OK);
    }

}
