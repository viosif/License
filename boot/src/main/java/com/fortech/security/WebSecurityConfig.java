package com.fortech.security;


import com.fortech.model.User;
import com.fortech.repository.UserRepository;
import com.fortech.repository.UserRolesRepository;
import com.fortech.services.MyUserDetailsService;
import com.fortech.model.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by iosifvarga on 07.07.2017.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private MyUserDetailsService authenticationProvider;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println(auth + "--------------------------------------->");

        userRepository.deleteAll();
        userRolesRepository.deleteAll();

        UserRoles userAdmin = new UserRoles("ADMIN");
        UserRoles userUser = new UserRoles("USER");

        userAdmin = userRolesRepository.save(userAdmin);
        userUser = userRolesRepository.save(userUser);

        User admin = new User("useradmin", "useradmin", userAdmin);
        User user1 = new User("user1", "user1pass", userUser);
        User user2 = new User("user2", "user2pass", userUser);

        userRepository.save(admin);
        userRepository.save(user1);
        userRepository.save(user2);

        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("ADMIN");
        auth.userDetailsService(authenticationProvider);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //HTTP Security
        http.httpBasic().authenticationEntryPoint((request, response, authException) -> {
            String requestBy = request.getHeader("X-Requested-By");
            if (requestBy == null || requestBy.isEmpty()) {
                HttpServletResponse httpServletResponse = response;
                httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, authException.getMessage());
            } else {
                HttpServletResponse httpServletResponse = response;
                httpServletResponse.addHeader("WWW-Authenticate", "Application driven");
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        })
                .and().authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .anyRequest().authenticated()
                .antMatchers("/swagger-ui.html").permitAll()
                .and().logout()
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).ignoringAntMatchers("/client/", "/client/deleteByEmail","/client/addLicenseToClient","/client/deleteLicenseFromClient", "/client/findByEmail", "/license/", "/license/deleteLicenseByKey");
        //.and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
    }


    public class CsrfHeaderFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
            if (csrf != null) {
                Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                String token = csrf.getToken();
                String id = request.getSession(true) != null ? request.getSession(false).getId() : "<NO SESSION>";
                if (cookie == null || token != null && !token.equals(cookie.getValue())) {
                    cookie = new Cookie("XSRF-TOKEN", token);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }

                response.setHeader("X-XSRF-TOKEN", token);
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
                response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, x-xsrf-token, x-auth-token, X-Auth-Token, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, authorization");
            }
            filterChain.doFilter(request, response);
        }
    }//end class CsrfHeaderFilter

}//end class WebSecurityConfig
