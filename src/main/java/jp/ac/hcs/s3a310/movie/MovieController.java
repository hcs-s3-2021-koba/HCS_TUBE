package jp.ac.hcs.s3a310.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MovieController {

	@Autowired
	MovieService movieService;

	/**
	 *
	 * @return 動画視聴画面
	 */
	@GetMapping("/watcthMovie/{id}")
	public String watchMovie(@PathVariable("id") String movie_id, Model model) {
		MovieData movieData = movieService.selectMovie(movie_id);
		model.addAttribute("movieData", movieData);
		return "movie/watch_video";

	}
}
