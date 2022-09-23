package com.deadline.knunotice.config.jsonwebtoken;

import com.deadline.knunotice.member.MemberAuthentication;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenProviderService {

    @Value("${jwt_secret_access_key}")
    private String jwtAccessKey;

    @Value("${jwt_secret_refresh_key}")
    private String jwtRefreshKey;

    @Value("${jwt_access_expiration_in-ms}")
    private int jwtAccessTime;

    @Value("${jwt_Refresh_expiration_in-ms}")
    private int jwtRefreshTime;

    /**
     * jwt 토큰 생성
     * @param authentication 인증 객체
     * @return token
     */
    public String generateToken(Authentication authentication, int type) {

        MemberAuthentication customUserDetails = (MemberAuthentication) authentication;
        String email = customUserDetails.getEmail();

        String token;

        Date now = new Date();
        Date expiryDate;

        if(type == 0) {
            expiryDate = new Date(now.getTime() + jwtAccessTime);
            token = Jwts.builder()
                    .setSubject(email) // 사용자
                    .setIssuedAt(new Date()) // 현재 시간 기반으로 생성
                    .setExpiration(expiryDate) // 만료 시간 세팅
                    .signWith(SignatureAlgorithm.HS512, jwtAccessKey) // 사용할 암호화 알고리즘, signature에 들어갈 secret 값 세팅
                    .compact();
        } else {
            expiryDate = new Date(now.getTime() + jwtRefreshTime);
            token = Jwts.builder()
                    .setSubject(email) // 사용자
                    .setIssuedAt(new Date()) // 현재 시간 기반으로 생성
                    .setExpiration(expiryDate) // 만료 시간 세팅
                    .signWith(SignatureAlgorithm.HS512, jwtRefreshKey) // 사용할 암호화 알고리즘, signature에 들어갈 secret 값 세팅
                    .compact();
        }

        return token;
    }

    /**
     * Jwt 토큰에서 아이디 추출
     * @param token jwt
     * @return 아이디
     */
    public String getUserIdFromJWT(String token, int type) {
        Claims claims;
        if(type == 0) {
            claims = Jwts.parser()
                    .setSigningKey(jwtAccessKey)
                    .parseClaimsJws(token)
                    .getBody();
        } else {
            claims = Jwts.parser()
                    .setSigningKey(jwtRefreshKey)
                    .parseClaimsJws(token)
                    .getBody();
        }

        return claims.getSubject();
    }

    /**
     * Jwt 토큰 유효성 검사
     * @param token jwt
     * @return jwt 올바른지 확인
     */
    public boolean validateToken(String token, int type) {
        try {
            if(type == 0) {
                Jwts.parser().setSigningKey(jwtAccessKey).parseClaimsJws(token);
            } else {
                Jwts.parser().setSigningKey(jwtRefreshKey).parseClaimsJws(token);
            }
            return true;
        } catch (SignatureException ex) {
            //log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            //log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            //log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            //log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            //log.error("JWT claims string is empty.");
        }
        return false;
    }

}
