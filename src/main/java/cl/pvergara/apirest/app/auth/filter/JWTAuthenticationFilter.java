package cl.pvergara.apirest.app.auth.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.pvergara.apirest.app.auth.service.JWTService;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	
	private JWTService jwtService;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
			
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {

		this.authenticationManager = authenticationManager;
		//Personalizar url login
		//setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/ligin", "POST"));
		this.jwtService = jwtService;
	}




	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		String username = obtainUsername(request);
		username = (username != null) ? username : "";
		
		String password = obtainPassword(request);
		password = (password != null) ? password : "";
		
		log.info(username);
		
		UsernamePasswordAuthenticationToken  authToken = new UsernamePasswordAuthenticationToken(username, password);

		return authenticationManager.authenticate(authToken);
	}




	/**
	 * Cuando la autenticacion fue valida el proceso de respuesta caera en este metodo.
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
	
		//De esta forma se puede obtener el nombre del usuario.
		//String username = ((User) authResult.getPrincipal()).getUsername();
		
		String token = jwtService.create(authResult);
		
		response.addHeader("Authorization", "Bearer "+ token);
		Map<String, Object> body = new HashMap<>();
		body.put("token", token);
		body.put("user", (User) authResult.getPrincipal());
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(200);
		response.setContentType("application/json");
		//super.successfulAuthentication(request, response, chain, authResult);
	}




	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {

		Map<String, Object> body = new HashMap<>();
		body.put("mensaje", "Usuario o contraseña no valido.");
		body.put("error", failed.getMessage());
		
		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
		response.setStatus(401);
		response.setContentType("application/json");
	}
	

	

	
}
