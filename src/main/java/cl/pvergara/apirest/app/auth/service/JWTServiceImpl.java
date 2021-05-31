package cl.pvergara.apirest.app.auth.service;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cl.pvergara.apirest.app.auth.SimpleGrantedAuthorityMixin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTServiceImpl  implements JWTService {

	public static final Key SECRET_KEY = new SecretKeySpec(
			Base64.getDecoder().decode( "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4"), 
            SignatureAlgorithm.HS256.getJcaName());
	
	public static final String PREFIX_TOKEN = "Bearer ";
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public String create(Authentication auth) throws JsonProcessingException {
		
		final Collection<? extends GrantedAuthority> roles = auth.getAuthorities();
		final Claims claims = Jwts.claims();
		claims.put("authorities", new ObjectMapper().writeValueAsString(roles));
		
		final String token = Jwts.builder()
				.setSubject(auth.getName())
				.setClaims(claims)
				.signWith(SECRET_KEY)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 3600000*2)) //2 horas para que expire
				.compact();
		
		return token;
	}

	@Override
	public boolean validate(String token) {
		
		try {
			
			getClaims(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			
			log.error("Error al obtener la session.", e);
		}

		return false;
	}

	@Override
	public Claims getClaims(String token) {

		final Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(resolve(token))
                .getBody();
		return claims;
	}

	@Override
	public String getUsername(String token) {
		// TODO Auto-generated method stub
		return getClaims(token).getSubject();
	}

	@Override
	public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
		
		Object roles = getClaims(token).get("authorities");
		
		return Arrays.asList(
				new ObjectMapper()
				.addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
				.readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class)
		);
	}

	@Override
	public String resolve(String token) {
		
		if (token != null && token.startsWith(PREFIX_TOKEN)) {
			return token.replace(PREFIX_TOKEN, "");
		}
		return "";
	}

	@Override
	public boolean requiresAuthentication(String token) {
		
		return (token == null || !token.startsWith(PREFIX_TOKEN));
	}

}
