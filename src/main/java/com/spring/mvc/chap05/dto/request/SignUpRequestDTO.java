package com.spring.mvc.chap05.dto.request;

import com.spring.mvc.chap05.entity.Member;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class SignUpRequestDTO {

    @NotBlank
    @Size(min = 4, max = 14)
    private String account;
    @NotBlank
    private String password;
    @NotBlank
    @Size(min = 2, max = 6)
    private String name;
    @NotBlank
    @Email
    private String email;

    // 프로필 사진 파일
    private MultipartFile profileImage;

    private Member.LoginMethod loginMethod;

    // 엔터티로 변환하는 유틸메서드
    public Member toEntity(PasswordEncoder encoder, String savePath) {
        return Member.builder()
                .account(account)
                .password(encoder.encode(password))
                .email(email)
                .name(name)
                .profileImage(savePath)
                .loginMethod(loginMethod)
                .build();
    }

}
