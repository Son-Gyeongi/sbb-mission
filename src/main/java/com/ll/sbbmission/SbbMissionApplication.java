package com.ll.sbbmission;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 스프링부트의 모든 설정이 관리
public class SbbMissionApplication {
    // 모든 프로그램에는 시작을 담당하는 파일

    public static void main(String[] args) {
        SpringApplication.run(SbbMissionApplication.class, args);
    }

}
