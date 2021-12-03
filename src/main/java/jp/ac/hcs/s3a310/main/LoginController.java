package jp.ac.hcs.s3a310.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
* ログインに関する機能・画面を制御する
*/
@Controller
public class LoginController{
	/**
	 * ログイン画面を表示する
	 * @param model ログイン情報を受け渡す
	 * @return ログイン画面
	 */
	@GetMapping("/login")
	public String getLogin(Model model) {
		return "login";
	}

	/**
	 *入力されたユーザIDとパスワードでログイン処理を行う
	 * @param model
	 * @param user_id
	 * @param password
	 * @return top画面
	 */
	@PostMapping("/login")
	public String checkLogin(Model model, @RequestParam("user_id") String user_id, @RequestParam("password") String password) {
		LoginService loginService = new LoginService();
		boolean login_flg = false;
		login_flg =  loginService.checkLogin(user_id, password);
		return null;

	}
}