package springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/showMyLoginPage")
	public String showMyLoginPage(){
		//return "plain-login";	//vodi me na moju formu koju nisam stilizovao
		return "fancy-login";	//sada zelim da me odvede na formu koju sam stilizovao u bootstrap-u
	}
	
	//add request mapping for /access-denied	-	ova metoda je za resavanje kada user koji ima drugu rolu
	@GetMapping("/access-denied")				   //a hoce da pristupi putanji druge role
	public String showAccessDenied(){
		return "access-denied";
	}
	
}
