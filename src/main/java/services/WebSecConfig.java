package services;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class WebSecConfig extends WebSecurityConfigurerAdapter {
    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin()
                //.loginPage("/login").permitAll()
                //.loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                //.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .and()
                .authorizeRequests()
                .antMatchers("/home", "/register").permitAll()
                .antMatchers("/webjars/bootstrap/**", "/webjars/jquery/**", "/webjars/popper.js/**").permitAll()
                .antMatchers("/statistics").hasRole("ADMIN")
                //.antMatchers("/users", "/user/changeRole").hasRole("ADMIN")
                .anyRequest().authenticated();
    }

}
