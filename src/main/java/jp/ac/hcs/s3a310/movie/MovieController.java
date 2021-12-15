package jp.ac.hcs.s3a310.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

	@PostMapping("/comment/fix")
	public String fixMovie(@PathVariable("movie_id") String id ) {

		return null;

	}
}
