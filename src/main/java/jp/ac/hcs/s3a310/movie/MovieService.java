package jp.ac.hcs.s3a310.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

	@Autowired MovieRepository movieRepository;

	/**
	 * 動画情報を全件取得する
	 * @return MovieEntity
	 */
	public MovieEntity selectAll() {
		return movieRepository.selectAll();
	}

}
