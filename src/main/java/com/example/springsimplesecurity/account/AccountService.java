package com.example.springsimplesecurity.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService implements UserDetailsService { //WebSecurityConfig의 UserDetails를 여기서 설정한다

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account findAccount = accountRepository.findByEmail(username);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        //스프링 시큐리티에있는 객체로 UserDetails를 구현하는 클래스
        return  new User(findAccount.getEmail(), findAccount.getPassword(), authorities);

        //원래 이 코드드처럼 장황한데 위처럼 줄일 수 있다.
        /*
        //UserDatails란 대부분의 웹사이트에서 유저가 가지고있을 프로퍼티들을 가지고 추상화된 클래스
        //이것을 우리가 만든 도메인인 Account와 호환되도록 수정해야한다
        UserDetails userDetails = new UserDetails() {

            //해당 유저의 권한
           @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
               List<GrantedAuthority> authorities = new ArrayList<>();
               authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                return authorities;
            }

            @Override
            public String getPassword() {
                return findAccount.getPassword();
            }

            @Override
            public String getUsername() {
                return findAccount.getEmail();  //우리는 지금 email을 username으로 사용한다. 주의할점은 유일한 값을 사용해야한다는점
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {    //계정 비활성화 기능같은거 사용할때
                return true;
            }
        };

        return userDetails;
        */
    }

    public Account save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }
}
