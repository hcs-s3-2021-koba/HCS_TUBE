package jp.ac.hcs.s3a310.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

/**
* ログインに関する機能・画面を制御する
*/
@Slf4j
@Controller
public class LoginController{

<<<<<<< HEAD
		/**
		 * ログイン画面を表示する
		 * @param model ログイン情報を受け渡す
		 * @return ログイン画面
		 */
		@PostMapping("/loginProcess")
		public String getLogin(Model model) {
			return "user/login";
		}
=======
	/**
	 * ログイン画面を表示する
	 * @param model ログイン情報を受け渡す
	 * @return ログイン画面
	 */
	@GetMapping("/login")
	public String getLogin(Model model) {
		System.out.println("ろぐいんきちゃあああああああああ");
		return "user/login";
	}

	/**
	 *入力されたユーザIDとパスワードでログイン処理を行う
	 * @param model
	 * @param user_id
	 * @param password
	 * @return top画面
	 */
	@PostMapping("/user/loginProcess")
	public String checkLogin(Model model, @RequestParam("user_id") String user_id, @RequestParam("password") String password) {
		LoginService loginService = new LoginService();
		System.out.println("user_id" + "password");
		// パスワードの暗号化
		password = loginService.encryptionPassword(password);
		System.out.println("password");

		boolean login_flg = false;
		login_flg =  loginService.checkLogin(user_id, password);
		return null;

	}
>>>>>>> branch 'main' of https://github.com/hcs-s3-2021-koba/HCS_TUBE.git
}