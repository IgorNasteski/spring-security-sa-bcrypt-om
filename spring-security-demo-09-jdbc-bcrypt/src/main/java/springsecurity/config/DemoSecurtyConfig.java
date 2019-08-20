package springsecurity.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

@Configuration
@EnableWebSecurity
public class DemoSecurtyConfig extends WebSecurityConfigurerAdapter {

	/*U OVOJ KLASI JE CELA PRICA OKO LOGINA. SPRING CE NAM ODRADITI AUTENTIFIKACIJU A POSLE I
	AUTORIZACIJU KORISNIKA. ZA POCETAK KADA OVERRIDUJEMO PRVU METODU "configure(AuthenticationManagerBuilder auth)"
	I KADA POKRENEMO PROJEKAT, SPRING CE NAS BACITI NA DEFAULTNO KREIRAN LOGIN SA PONUDJENIM UNOSOM
	USERNAMEA I PASSWORDA. AKO ZELIMO(A ZELECEMO) DA STILIZUJEMO TU FORMU TO RADIMO U METODI
	"configure(HttpSecurity http)" - TU JE OBJASNJEN KORAK PO KORAK KAKO DA KAZEMO DA ZELIMO DA NAS PRI POKRETANJU
	NASE APLIKACIJE SPRING POSALJE (PREKO SERVLETA - "LoginController")
	NA NASU STILIZOVANU FORMU A NE NA NJEGOVU DEFAULTNU NE-STILIZOVANU.*/
	
	
	//injectujem primerak klase za security data source koja se nalazi u klasi "DemoAppConfig"
	//@Bean
	//public DataSource securityDataSource(){...}	A TU JE CELA KONFIGURACIJA ZA BAZU
	@Autowired
	private DataSource securtyDataSource;
	
	
	//MORAMO OVERRIDOVATI OVAJ METOD, DESNI KLIK/SOURCE/OVERRIDE-IMPLEMENTS-METHODS
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		//DO SAD SMO HARDKODIRALI USERE OVDE(NISMO IH IMALI U BAZI) A SADA CEMO ISPOD OVOG ISKOMENTARISANOG NJIH UZIMATI IZ BAZE
		/*
		 * //add our users for in memory authentication
		 * UserBuilder users = User.withDefaultPasswordEncoder();
		
		auth.inMemoryAuthentication()
			.withUser(users.username("john").password("test123").roles("EMPLOYEE"))
			.withUser(users.username("mary").password("test123").roles("EMPLOYEE","MANAGER"))
			.withUser(users.username("susan").password("test123").roles("EMPLOYEE","ADMIN"));*/
		
		//USE JDBC AUTHENTICATION - UZIMAMO USERE IZ BAZE PA IM NAMESTAMO PATH ZA RAZLICITE ROLE
		auth.jdbcAuthentication().dataSource(securtyDataSource);	//"securtyDataSource" - AUTOWIRED injectovan u ovu klasu
		//BITNO !!!! : U BAZI POLJA USERA MORAJU DA SE ZOVU "username" i "password" DA BI SPRING SECURITY MOGAO DA ODRADI
		//CELU PRICU JER SU NJEMU DEFAULTNA POLJA ZA SECURITY OVA DVA NAVEDENA. TAKODJE DRUGA TABELA MORA DA SE ZOVE
		//"authorities" - ISTO STO I "roles" KAO ROLE. TA TABELA CE IMATI POLJE "authority"
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				//.anyRequest().authenticated()	//KAZEMO DA SVAKO KO KORISTI NASU APP MORA NA POCETKU DA SE OBAVEZNO LOGUJE
				//OVO SADA UKLANJAMO JER ZELIMO DA NAS APLIKACIJA VODI NA NEKOLIKO STRANA BAZIRANO NA ROLI USERA, NPR JOHN
				//MORA VIDETI SAMO PUTANJU ZA "EMPLOYEE" JER MU JE TO ROLA, MARY TREBA DA ODE NA ISKLJUCIVO SAMO ZA "MANAGER", A
				//SUSAN MORA DA BUDE PREUSMERENA NA PUTANJU REZERVISANU ISKLJUCIVO ZA "ADMIN"
				
				.antMatchers("/").hasRole("EMPLOYEE")//SVAKO MOZE DA PRISTUPI home page AKO IMA ROLU "EMPLOYEE" (U OVOM SLUCAJU SVA 3 USERA)
				.antMatchers("/leaders/**").hasRole("MANAGER")//NA OVU GRANU SU ISKLUCIVO PREUSMERENI ONI KOJI IMAJU ROLU "MANAGER" - mary
				.antMatchers("/systems/**").hasRole("ADMIN")//NA OVU GRANU SU ISKLUCIVO PREUSMERENI ONI KOJI IMAJU ROLU "ADMIN" - susan
				.and()
				.formLogin()
					.loginPage("/showMyLoginPage") //KAZEMO DA UMESTO DA KORISTI DEFAULTNU-NE STILIZOVANU VERZIJU LOGINA KOJI
													//NAM SPRING PO DEFAULTU OMOGUCAVA, MI KAZEMO IDI NA OVU PUTANJU
													//SERVLETA(LoginController) PA U NJOJ NA JSP STRANICU KOJU VRACAMO(NASA STILIZOVANA FORMA ZA LOGIN)
					.loginProcessingUrl("/authenticateTheUser")	//OVDE NAS FORMA DALJE SALJE KADA NAKON UNOSA USERNAMEA
															//I PASSWORDA OPALIMO SUBMIT(TAKODJE SERVLET PUTANJA)
															//A U FORMI JE action="authenticateTheUser" ZA SUBMIT
					.permitAll()
				.and()
				.logout().permitAll()	//DODAJEM MOGUCNOST/OPCIJU ZA LOGOUT !!!!!
				.and()
				.exceptionHandling().accessDeniedPage("/access-denied");//KADA USER KOJI IMA ROLU NPR EMPLOYEE PROBA DA UDJE NA 
														//PUTANJU/PATH ZA NPR MANAGERA ILI MANAGER PROBA DA UDJE PUTANJU ZA
														//ADMINA ITD, OVOM LINIJOM KODA KAZEMO DA KADA SE KLIKNE I DESI SE ERROR
														//NAS POSALJE NA CONTROLER "access-denied" PA ONDA TU KAZEMO NA KOJU JSP
														//STRANICU NAS SALJE(U NJOJ ISPISUJEMO NAS TEXT DA IM NIJE OMOGUCENO
														//OVAJ "access-denied" CEMO SMESTITI U LoginController JER TO IMA SMISLA
														//VISE NEGO DA GA STAVIMO U ControllerDemo (STAVLJAM U KONTROLER PO ZELJI)
	}

}
