package jp.ac.hcs.s3a310.live;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LiveController {

	@RequestMapping("/liveMovie")
	public String getLogin(Model model) {
		return "movie/live_control";
	}

}
