package jp.ac.hcs.s3a310.movie;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ac.hcs.s3a310.comment.CommentEntity;
import jp.ac.hcs.s3a310.comment.CommentService;
import jp.ac.hcs.s3a310.main.StartUpController;

@Controller
public class MovieController {

	@Autowired
	MovieService movieService;

	@Autowired
	CommentService commentService;

	@Autowired
	StartUpController startUpController;

	/**
	 *
	 * @return 動画視聴画面
	 */
	@GetMapping("/watcthMovie/{id}")
	public String watchMovie(@PathVariable("id") String movie_id, Model model) {
		/** 動画データ取得 */
		MovieData movieData = movieService.selectMovie(movie_id);

		/** コメントデータ取得 */
		CommentEntity commentEntity = commentService.selectComment(movie_id);

		model.addAttribute("movieData", movieData);
		model.addAttribute("commentEntity", commentEntity);

		return "movie/watch_video";

	}

	@GetMapping("/movie/update/{id}")
	public String getUserUpdate(@ModelAttribute @Validated MovieFormForUpdate form, @PathVariable("id") String movie_id, Model model) {
		model.addAttribute("movie_id", movie_id);
		return "movie/upload_fix";
	}

	/**
	 * 1件分のMovie情報でデータベースを更新する.
	 * @param form 更新する動画情報
	 * @param bindingResult データバインド実施結果
	 * @param principal 情報
	 * @param model 値を受け渡す
	 * @return ユーザ一覧画面
	 */
	@PostMapping(value = "/watcthMovie/detail/{id}")
	public String postUserDetailUpdate(@PathVariable("id") String movie_id,@ModelAttribute @Validated MovieFormForUpdate form,
			BindingResult bindingResult,
			Principal principal,
			Model model) {

		int id = Integer.parseInt(movie_id);
		MovieData data = new MovieData();
		data.setMovie_id(id);
		data.setMovie_title(form.getMovie_title());
		data.setMovie_detail(form.getMovie_detail());

		boolean result = movieService.updateOne(data);

		return watchMovie(movie_id,model);

	}

	@PostMapping(value = "/watcthMovie/delete/{id}")
	public String movieDelete(@PathVariable("id") String movie_id, @ModelAttribute @Validated MovieFormForUpdate form,
			BindingResult bindingResult, Principal principal, Model model) {
		int id = Integer.parseInt(movie_id);
		movieService.deleteOne(movie_id);
		return startUpController.getLogin(model);
	}

	/**
	 * ユーザ情報を検索する
	 * @param category カテゴリー
	 * @param keyword キーワード
	 * @param principal ログインユーザ
	 * @param model モデル
	 * @return ユーザ一覧画面
	 */
	@PostMapping("/movie/search")
	public String searchReport(@RequestParam("keyword") String keyword, Principal principal, Model model ) {
		//エンティティクラスを作成
		MovieEntity movieEntity = new MovieEntity();
		movieEntity = movieService.selectSearch(keyword);
		System.out.println(movieEntity);
		model.addAttribute("movieEntity" , movieEntity);

		return "/top";
	}
}
