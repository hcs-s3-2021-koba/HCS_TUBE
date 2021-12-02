package jp.ac.hcs.s3a310.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StartUpController {

	/**
	 * ログイン画面を表示する
	 * @param model モデル
	 * @return ログイン画面
	 */
	@GetMapping("/start")
	public String getLogin(Model model) {
		return "index";
	}
}