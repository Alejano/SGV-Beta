package com.PGJ.SGV;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.PGJ.SGV.auth.handler.LoginSuccesHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private DataSource dataSource;	
	@Autowired
	private LoginSuccesHandler succesHandler;
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.headers().frameOptions().sameOrigin();
	
		http.authorizeRequests().antMatchers("/CSS/**","/assets/**", "/js/**", "/images/**","/index").permitAll()
		.antMatchers("/Dashboard").hasAnyRole("ADMIN")
		.antMatchers("/home").hasAnyRole("ADMIN","USER","TALLER","SINIESTRO","SEGURO")
		.antMatchers("/").hasAnyRole("ADMIN","USER","TALLER","SINIESTRO","SEGURO")
		.antMatchers("/api/search").hasAnyRole("ADMIN","USER")
		.antMatchers("/Adscripciones").hasAnyRole("ADMIN")
		.antMatchers("/BuscarVehiculos").hasAnyRole("ADMIN","USER")
		.antMatchers("/Combustibles").hasAnyRole("ADMIN","USER")
		.antMatchers("/talleres").hasAnyRole("ADMIN")
		.antMatchers("/Mantenimientos").hasAnyRole("ADMIN","USER")
		.antMatchers("/Usuarios").hasAnyRole("ADMIN")
		.antMatchers("/Conductores").hasAnyRole("ADMIN","USER")
		.antMatchers("/Viajes").hasAnyRole("ADMIN","USER")
		.antMatchers("/Aseguradoras").hasAnyRole("ADMIN","USER","SEGURO")	
		.antMatchers("/Seguros").hasAnyRole("ADMIN","USER","SEGURO")
		
		.anyRequest().authenticated().and()
	    .formLogin()
	    .successHandler(succesHandler)
	    .loginPage("/index")
	    .defaultSuccessUrl("/home", true)
	    .failureUrl("/login-error")
	    .permitAll().and()
	    .logout().logoutSuccessUrl("/index").permitAll();

	}
	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
			builder.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(passwordEncoder)
			.usersByUsernameQuery("select no_empleado, contrasena,enabled from usuarios where no_empleado=?")
			.authoritiesByUsernameQuery("select a.id,a.authority from authorities a inner join usuarios u on (a.id=u.authority_id) where u.no_empleado=?");
		/*PasswordEncoder encoder = passwordEncoder;
		UserBuilder users = User.builder().passwordEncoder(encoder::encode);

		builder.inMemoryAuthentication()
				.withUser(users.username("0").password("123").roles("ADMIN", "USER"))				
				.withUser(users.username("1").password("123").roles("USER"));*/

	}
	
	
	
	

}
