package models.responses;

import lombok.Builder;

@Builder
public record AuthenticateResponse(
        String token,
        String type
) {
}
