package com.example.diplomskiBackend.security.config;

import com.example.diplomskiBackend.security.rest.RestAuthenticationEntryPoint;
import com.example.diplomskiBackend.security.token.TokenAuthenticationFilter;
import com.example.diplomskiBackend.security.token.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private TokenUtils tokenUtils;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.headers().cacheControl().disable();
        httpSecurity
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
				.authorizeRequests()
                    .antMatchers("/api/auth/**").permitAll()
                    .antMatchers("/api/sql/**").permitAll()
                    .antMatchers(HttpMethod.GET,"/api/playlist/{id}").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/register").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/channel/{id}").permitAll()
                    .antMatchers("/api/videos").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/videos/{id}").permitAll()
                .anyRequest().permitAll().and()
                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, userDetailsService), BasicAuthenticationFilter.class);
    }
}
