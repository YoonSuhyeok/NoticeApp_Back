package com.deadline.knunotice.member;

import lombok.Getter;

@Getter
public class TokenResponseDTO {

    private final String accessToken;

    private final String refreshToken;

    public TokenResponseDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
