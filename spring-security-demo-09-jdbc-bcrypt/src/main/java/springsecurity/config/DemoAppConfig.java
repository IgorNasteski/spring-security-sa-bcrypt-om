package springsecurity.config;

import java.beans.PropertyVetoException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration	//kazemo da nam je ova klasa Konfiguracija, tj da umesto u xml fajlu ovde to sve regulisemo
@EnableWebMvc	//nesto kao <mvc:annotation-driven/>
@ComponentScan(basePackages="springsecurity")	//zadajemo putanju paketa za skeniranje componenata(beanova)
@PropertySource("classpath:persistence-mysql.properties")  //UBACUJEMO PROPERTIES FILE I CITAMO GA
public class DemoAppConfig {

	//KREIRAM VARIJABLU KOJOM ULAZIM/PRISTUPAM U PROPERTIES(ONA SADRZI PROPERTI)
	@Autowired
	private Environment env;	//sa env pristupam podacima u properties fajlu za mysql
	
	//setujem/omogucavam logger - da mogu da zalogujem sve sto radim da bih znao sta se desava
	private Logger logger = Logger.getLogger(getClass().getName());
	
	//define a bean for ViewResolver
	@Bean
	public ViewResolver viewResolver(){
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
	
	//definisem/kreiram bean za nas security datasource
	//KONFIGURISEM CELU PRICU SA BAZOM
	@Bean
	public DataSource securityDataSource(){
		//kreiram connection pool
		ComboPooledDataSource securityDataSource = new ComboPooledDataSource();
		
		//setujem jdbc driver
		try {
			securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}
		
		//logujem connection props
		logger.info(">>> jdbc.url=" + env.getProperty("jdbc.url"));
		logger.info(">>> jdbc.url=" + env.getProperty("jdbc.user"));
		
		//setujem bazin connection props
		securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));//citamo konfiguraciju iz properties file-a i setujemo ovde
		securityDataSource.setUser(env.getProperty("jdbc.user"));
		securityDataSource.setPassword(env.getProperty("jdbc.password"));
		
		//setujem connection pool props
		securityDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
		securityDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
		securityDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
		securityDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));
		
		return securityDataSource;
	}
	
	//trebace mi pomocni metod da konvertuje String u int jer sve sto dohvatam iz property file-e stize kao String a meni
	//ce trebati kao int
	private int getIntProperty(String propName){
		String propVal = env.getProperty(propName);
		
		//sada konvertovati u int
		int intPropVal = Integer.parseInt(propVal);
		
		return intPropVal;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
