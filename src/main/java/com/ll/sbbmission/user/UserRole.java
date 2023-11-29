package com.ll.sbbmission.user;

import lombok.Getter;

// 스프링 시큐리티는 인증 뿐만 아니라 권한도 관리
@Getter
public enum UserRole { // enum / 열거 자료형,  상수 자료형
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
