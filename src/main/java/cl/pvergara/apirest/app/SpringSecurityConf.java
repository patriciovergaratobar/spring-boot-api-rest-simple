package cl.pvergara.apirest.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import cl.pvergara.apirest.app.auth.filter.JWTAuthenticationFilter;
import cl.pvergara.apirest.app.auth.filter.JWTAuthorizationFilter;
import cl.pvergara.apirest.app.auth.service.JWTService;
import cl.pvergara.apirest.app.models.service.JpaUserDetailsService;

@Configuration
public class SpringSecurityConf extends WebSecurityConfigurerAdapter {
	

	@Autowired
	private JpaUserDetailsService userDetailService;

	@Autowired
	private JWTService jwtService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncode() {
		return new BCryptPasswordEncoder();
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/", "/usuario").permitAll() //Todas los accesos publicos
		.antMatchers("/usuario/**", "/tarea/**", "/estado/**")
	//s	.hasAnyRole("USER") //Los accesos privador
	//	.anyRequest()
		.authenticated()
		/*.and()
		.formLogin().permitAll() //Activa un formulario de login de prueba
		.and()
		.logout().permitAll()  //Activa un formulario de logout prueba 
		*/
		.and()
		.addFilter(new JWTAuthenticationFilter(authenticationManager(), this.jwtService))
		.addFilter(new JWTAuthorizationFilter(authenticationManager(), this.jwtService))
		.csrf().disable() //Se deshabilita el ingreso por formulario
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Activamos la session sin estado
		;
	}


	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
		
		builder.userDetailsService(userDetailService)
		.passwordEncoder(passwordEncode());
		
	}
}
