package md.fiodorov.security.config

import md.fiodorov.entity.Role
import md.fiodorov.security.jwt.JWTAuthenticationFilter
import md.fiodorov.security.jwt.JWTLoginFilter
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.session.SessionManagementFilter

/**
 * @author rfiodorov
 * on 19/11/17.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.
                csrf()
                .disable()
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/user/**", "/questions/**", "/public/**").permitAll()
                .anyRequest().authenticated()
                .antMatchers("/questions/**/answers").hasAuthority(Role.GUEST.name)
                .and()
                .addFilterBefore(CorsFilter(), SessionManagementFilter::class.java)
                .addFilterBefore(JWTLoginFilter("/user/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter::class.java)
                .addFilterBefore(JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }
}
