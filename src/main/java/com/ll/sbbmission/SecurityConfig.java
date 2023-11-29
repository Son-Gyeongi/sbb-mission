package com.ll.sbbmission;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 스프링의 환경설정 파일임을 의미
@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션
@EnableMethodSecurity(prePostEnabled = true) // 로그인 여부를 판별하기 위해 사용했던 @PreAuthorize 애너테이션을 사용하기 위해 반드시 필요하다.
public class SecurityConfig {

    /*
    @EnableWebSecurity 애너테이션을 사용하면 내부적으로 SpringSecurityFilterChain이 동작하여 URL 필터가 적용된다.
    스프링 시큐리티의 세부 설정은 SecurityFilterChain 빈을 생성하여 설정
    */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        (authorizeHttpRequests) -> authorizeHttpRequests
                                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
                        // 모든 인증되지 않은 요청을 허락
                )
                // 스프링 시큐리티를 적용하면 CSRF 기능이 동작, H2 콘솔은 예외로 처리
                .csrf(
                        (csrf) -> csrf
                                .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                        // /h2-console/로 시작하는 URL은 CSRF 검증을 하지 않는다
                )
                // 스프링 시큐리티는 사이트의 콘텐츠가 다른 사이트에 포함되지 않도록 하기 위해 X-Frame-Options 헤더값을
                // 사용하여 이를 방지한다. (clickjacking 공격을 막기위해 사용함)
                .headers(
                        (headers) -> headers
                                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN
                                ))
                )
                .formLogin( // 스프링 시큐리티에서 구현한 로그인
                        (formLogin) -> formLogin
                                .loginPage("/user/login") // 로그인 설정을 담당하는 부분으로 로그인 페이지의 URL
                                .defaultSuccessUrl("/") // 로그인 성공시에 이동하는 디폴트 페이지는 루트 URL
                )
                .logout( // 스프링 시큐리티에서 구현한 로그아웃
                        (logout) -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // 로그아웃 URL
                                .logoutSuccessUrl("/") // 로그아웃이 성공하면 루트(/) 페이지로 이동
                                .invalidateHttpSession(true) // 로그아웃시 생성된 사용자 세션도 삭제하도록 처리
                )
        ;

        return http.build();
    }

    /*
    BCryptPasswordEncoder 객체를 직접 new로 생성하는 방식보다는 PasswordEncoder 빈(bean)으로
    등록해서 사용하는 것이 좋다. 왜냐하면 암호화 방식을 변경하면 BCryptPasswordEncoder를 사용한
    모든 프로그램을 일일이 찾아서 수정해야 하기 때문
    PasswordEncoder는 BCryptPasswordEncoder의 인터페이스
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        // BCrypt 해싱 함수(BCrypt hashing function)를 사용해서 비밀번호를 암호화
        return new BCryptPasswordEncoder();
    }

    // 스프링 시큐리티의 인증을 담당
    // 사용자 인증시 UserSecurityService와 PasswordEncoder를 사용
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
