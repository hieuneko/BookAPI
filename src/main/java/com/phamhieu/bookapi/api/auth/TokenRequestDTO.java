package com.phamhieu.bookapi.api.auth;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequestDTO {

    private String idToken;
}
