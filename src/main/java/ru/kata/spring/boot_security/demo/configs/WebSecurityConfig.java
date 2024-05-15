package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import ru.kata.spring.boot_security.demo.security.AuthProviderImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final AuthProviderImpl authProviderImpl;

    public WebSecurityConfig(SuccessUserHandler successUserHandler, AuthProviderImpl authProviderImpl) {
        this.successUserHandler = successUserHandler;
        this.authProviderImpl = authProviderImpl;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    // аутентификация inMemory
    // @Bean
    // @Override
    // public UserDetailsService userDetailsService() {
    //     UserDetails user =
    //             User.withDefaultPasswordEncoder()
    //                     .username("user")
    //                     .password("user")
    //                     .roles("USER")
    //                     .build();

    //     return new InMemoryUserDetailsManager(user);
    // }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.authProviderImpl);
    }
}