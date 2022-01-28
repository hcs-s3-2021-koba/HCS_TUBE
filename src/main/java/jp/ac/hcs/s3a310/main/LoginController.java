package jp.ac.hcs.s3a310.main;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
* ログインに関する機能・画面を制御する
*/
@Controller
@SpringBootApplication
public class LoginController extends SpringBootServletInitializer {
	/**
	 * ログイン画面を表示する
	 * @param model ログイン情報を受け渡す
	 * @return ログイン画面
	 */
	@GetMapping("/login")
	public String getLogin(Model model) {
		return "user/login";
	}

	@Override
	  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	      return builder.sources(LoginController.class);
	    }
}