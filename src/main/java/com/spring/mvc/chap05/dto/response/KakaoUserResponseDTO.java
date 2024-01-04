package com.spring.mvc.chap05.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Setter@Getter@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class KakaoUserResponseDTO {

    private Long id;

    @JsonProperty("connected_at")
    private LocalDateTime connectedAt;

    private Properties properties;

    @Setter@Getter@ToString
    public static class Properties{
        private String nickname;
        @JsonProperty("profile_image")
        private String profileImage;
        @JsonProperty("thumbnail_image")
        private String thumbnailImage;
    }

}
