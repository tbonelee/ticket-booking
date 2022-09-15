package org.project.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Authorization server에 access 토큰 요청시 응답 포맷. RFC 6749 내용 준수. (<a
 * href="https://datatracker.ietf.org/doc/html/rfc6749#section-5.1">링크</a>)
 */
@ToString
@Getter
@NoArgsConstructor
public class OAuthAccessTokenResponse {

  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("token_type")
  private String tokenType;
  @JsonProperty("expires_in")
  private String expiresIn;
  @JsonProperty("refresh_token")
  private String refreshToken;
  private String scope;

}