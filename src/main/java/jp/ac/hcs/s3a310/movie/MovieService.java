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

	/**
	 * 動画情報を一件取得する
	 * @param movie_id
	 * @return data
	 */
	public MovieData selectMovie(String movie_id) {
		MovieData data = movieRepository.selectOne(movie_id);
		return data;
	}

	/**
	 * 1件更新する
	 * @param movieData 更新するユーザ情報(パスワードは設定しない)
	 * @return 処理結果(成功:true, 失敗:false)
	 */
	public boolean updateOne(MovieData movieData) {
		int rowNumber = movieRepository.updateOne(movieData);
		boolean result = (rowNumber > 0) ? true : false;
		return result;
	}

}
