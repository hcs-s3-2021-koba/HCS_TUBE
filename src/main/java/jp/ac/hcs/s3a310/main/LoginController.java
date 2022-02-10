package jp.ac.hcs.s3a310.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.ac.hcs.s3a310.SecurityConfig;
import jp.ac.hcs.s3a310.user.UserService;

/**
* ログインに関する機能・画面を制御する
*/
@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@Autowired
	SecurityConfig securityConfig;

	/**
	 * ログイン画面を表示する
	 * @param model ログイン情報を受け渡す
	 * @return ログイン画面
	 */
	@GetMapping("/login")
	public String getLogin(Model model) {
		return "user/user_guest_login";
	}

	@GetMapping("/user_login")
	public String getLoginUser(Model model) {
		return "user/user_login";
	}
}



