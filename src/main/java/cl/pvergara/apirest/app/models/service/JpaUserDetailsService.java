package cl.pvergara.apirest.app.models.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.pvergara.apirest.app.models.dao.IUsuarioDao;
import cl.pvergara.apirest.app.models.entity.Rol;
import cl.pvergara.apirest.app.models.entity.Usuario;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService  {

	@Autowired
	private IUsuarioDao usuarioDao;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioDao.getByUserName(username);
		if (usuario == null) {
			
			logger.error("El usuario es nulo");
			throw new UsernameNotFoundException("Usuario no existe "+ username);
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Rol rol : usuario.getRoles()) {
			
			authorities.add(new SimpleGrantedAuthority(rol.getAuthority()));
		}
		
		return new User(username, usuario.getClave(), true, true, true, true, authorities);
	}

}
