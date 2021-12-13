package jp.ac.hcs.s3a310.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MovieController {

	@Autowired
	MovieService movieService;

	@GetMapping("/watcthMovie")
	public String watchMovie() {
		return "movie/watch_video";

	}
}
