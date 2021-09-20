
package com.ninadkhire.pocmynotesapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/*
		 * http.cors() .and() .authorizeRequests().antMatchers("/api/ping", "/signup**",
		 * "/js/**", "/css/**", "/img/**") .permitAll().anyRequest().authenticated()
		 * .and() .formLogin().loginPage("/login").permitAll() .and()
		 * .logout().invalidateHttpSession(true).clearAuthentication(true)
		 * .logoutRequestMatcher(new
		 * AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
		 * .permitAll();
		 */
		
		
		/*
		 * http.authorizeRequests() .antMatchers("/login").permitAll()
		 * .and().formLogin();
		 */
		 
		
		/*
		 * http.csrf().disable() .authorizeRequests() .antMatchers("/login").permitAll()
		 * .anyRequest() .authenticated() .and() .httpBasic() .and() .formLogin();
		 */
		
		http.
			authorizeRequests()
			.antMatchers("**").permitAll();
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
