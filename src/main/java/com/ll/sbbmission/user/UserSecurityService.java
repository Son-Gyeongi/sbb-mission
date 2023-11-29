package com.ll.sbbmission.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 데이터베이스에서 사용자를 조회하는 서비스
// UserSecurityService는 스프링 시큐리티 로그인 처리의 핵심 부분
@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    /*
    loadUserByUsername 메서드는 사용자명으로 비밀번호를 조회하여 리턴하는 메서드
    loadUserByUsername 메서드에 의해 리턴된 User 객체의 비밀번호가 화면으로부터 입력 받은 비밀번호와
    일치하는지를 검사하는 로직을 내부적으로 가지고 있다.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);

        // null인지 아닌지 검사
        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        SiteUser siteUser = _siteUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        if ("admin".equals(username)) {
            // ADMIN 권한을 부여
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            // USER 권한을 부여
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        // User 객체를 생성
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}
