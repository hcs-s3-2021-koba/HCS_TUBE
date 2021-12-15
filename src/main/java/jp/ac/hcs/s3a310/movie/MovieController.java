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

import jp.ac.hcs.s3a310.comment.CommentEntity;
import jp.ac.hcs.s3a310.comment.CommentService;

@Controller
public class MovieController {

	@Autowired
	MovieService movieService;

	@Autowired
	CommentService commentService;

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

	@GetMapping("/movie/update")
	public String getUserUpdate(@ModelAttribute MovieFormForUpdate form, Model model) {
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
	@PostMapping(value = "/watcthMovie/detail", params = "update")
	public String postUserDetailUpdate(@ModelAttribute @Validated MovieFormForUpdate form,
			BindingResult bindingResult,
			Principal principal,
			Model model) {

//		log.info("[" + principal.getName() + "]ユーザ更新:" + form.toString());

		MovieData data = new MovieData();
		data.setMovie_id(form.getMovie_id());
		data.setMovie_title(form.getMovie_title());
		data.setMovie_detail(form.getMovie_detail());

		boolean result = movieService.updateOne(data);

//		if (result) {
//			log.info("[" + principal.getName() + "]ユーザ更新成功");
//			model.addAttribute("result", "ユーザ更新成功");
//		} else {
//			log.warn("[" + principal.getName() + "]ユーザ更新失敗");
//			model.addAttribute("result", "ユーザ更新失敗");
//		}

		return "movie/watch_video";
	}
}
