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

    private final JwtTokenProviderService jwtTokenProviderService;

    public MemberController(JwtTokenProviderService tokenProvider, MemberService memberService, JwtTokenProviderService jwtTokenProviderService) {
        this.tokenProvider = tokenProvider;
        this.memberService = memberService;
        this.jwtTokenProviderService = jwtTokenProviderService;
    }

    @GetMapping("")
    public ResponseEntity<Member> findMemberByUsername(@Param("username") String username) {
        Member member = memberService.findMemberByUsername(username);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PostMapping("/signIn")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Member member = memberService.findMemberByEmail(loginRequestDTO.email);
        MemberAuthentication memberAuthentication = new MemberAuthentication(member);
        String accessToken = jwtTokenProviderService.generateToken(memberAuthentication, 0);
        String refreshToken = jwtTokenProviderService.generateToken(memberAuthentication, 1);

        return new ResponseEntity<>(new TokenResponseDTO(accessToken, refreshToken), HttpStatus.OK);
    }

    @PostMapping("/signUp")
    public ResponseEntity<TokenResponseDTO> signUp(@RequestBody SignUpDTO signUpDTO) {
        MemberAuthentication member = memberService.signUp(signUpDTO);

        String accessToken = jwtTokenProviderService.generateToken(member, 0);
        String refreshToken = jwtTokenProviderService.generateToken(member, 1);

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO(accessToken, refreshToken);
        return new ResponseEntity<>(tokenResponseDTO, HttpStatus.OK);
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
