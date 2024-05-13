package kr.or.nextit.springmvc.regist;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data

// 파라미터가 없는 생성자 만들기
//@NoArgsConstructor

/* final이 붙어있는 필드만 생성자로 만들기
    target -> regist -> RegisterRequest 파일을 확인해보면
    풀 생성자는 사라지고 email 생성자만 확인 가능 */
@RequiredArgsConstructor
public class RegisterRequest {
    private final String email;
    private String password;
    private String confirmPassword;
    private String name;

/**
 * Lombok 라이브러리를 사용하여
 * toString, getter, setter, constructor 자동 생성하여
 * 기존 코드 무필요
 * */
    /*
    public RegisterRequest() {
    }

    public RegisterRequest(String email, String password, String confirmPassword, String name) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPasswordEqualToConfirmPassword() {
        return password.equals(confirmPassword);
    }*/
}
