package com.gussoft.mongomito.security;

import java.util.Date;

public record AuthResponse(
        String token,
        Date expiration
) {
}
