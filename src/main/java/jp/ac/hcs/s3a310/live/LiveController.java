package jp.ac.hcs.s3a310.live;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LiveController {

	@Autowired
	LiveService liveService = new LiveService();

	@RequestMapping("/liveMovie")
	public String getLogin(Model model,Principal principal) {
		return "movie/live_control";
	}

	@RequestMapping("/live_start")
	public String startLive(Model model,Principal principal) {
		return "movie/live_start";
	}

	@PostMapping("/live/insert")
	public String insertLive(Model model,Principal principal,LiveForm liveForm) {
		try {
		boolean flg=liveService.insertLive(model, principal, liveForm);
		}catch(Exception e) {
			// ここにエラーメッセージ挿入
			return "movie/live_start";
		}
		return "movie/live_control";

	}



}
