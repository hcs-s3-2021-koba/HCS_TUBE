package jp.ac.hcs.s3a310.movie;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MovieController {

	@GetMapping("/watcthMovie")
	public String watchMovie() {
		return "movie/watch_video";

	}
}
