package jp.ac.hcs.s3a310.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.ac.hcs.s3a310.live.LiveEntity;
import jp.ac.hcs.s3a310.live.LiveService;
import jp.ac.hcs.s3a310.movie.MovieEntity;
import jp.ac.hcs.s3a310.movie.MovieService;

@Controller
public class StartUpController {

	@Autowired
	MovieService movieService;

	@Autowired
	LiveService liveService;

	/**
	 * ログイン画面を表示する
	 * @param model モデル
	 * @return ログイン画面
	 */
	@GetMapping("/top")
	public String getLogin(Model model) {

		MovieEntity movieEntity = movieService.selectAll();
		model.addAttribute("movieEntity", movieEntity);

		LiveEntity entity=liveService.selectAll();



		if(entity.getLiveList().toString().equals("[]")) {
			model.addAttribute("msg","現在ライブ配信はされていません。");
		}else {
			model.addAttribute("liveEntity", entity);
		}





		return "/top";


	}
}
