package com.spring.mvc.chap05.dto.response;

import lombok.*;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class LoginUserResponseDTO {

    private String account;
    private String nickName;
    private String email;

}