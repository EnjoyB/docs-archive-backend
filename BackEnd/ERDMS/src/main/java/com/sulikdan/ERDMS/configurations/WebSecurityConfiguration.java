package com.sulikdan.ERDMS.configurations;

import com.sulikdan.ERDMS.services.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Daniel Šulik on 10-Oct-20
 *
 * <p>Class WebSecurityConfiguration is to set configuration to whole app, mainly to provide
 * security for users login/logout.
 */
@AllArgsConstructor
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final UserService userService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  //  @Bean
  //  public BCryptPasswordEncoder bCryptPasswordEncoder() {
  //    return new BCryptPasswordEncoder();
  //  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()
        .antMatchers("/sign-up/**", "/login/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
  }
}
