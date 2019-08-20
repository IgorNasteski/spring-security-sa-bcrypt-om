package springsecurity.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MySpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[]{ DemoAppConfig.class };	//povezujemo drugu konfig klasu "DemoAppConfig", isto kao u xml fajlovima
	}												//kao sto smo povezivali ta dva xml fajla
													//kao ova prica
											/*<servlet>
										    <servlet-name>dispatcher</servlet-name>
										    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
										    <init-param>
										      <param-name>contextConfigLocation</param-name>
										      <param-value>/WEB-INF/spring-mvc-crud-demo-servlet.xml</param-value>
										    </init-param>
										    <load-on-startup>1</load-on-startup>
										  </servlet>*/

	@Override									//kao ova prica: servlet mapping
	protected String[] getServletMappings() {	//<servlet-mapping>
		return new String[]{ "/" };					//<servlet-name>dispatcher</servlet-name>
	}												//<url-pattern>/</url-pattern>
												//</servlet-mapping>

}
 