package jp.ac.hcs.s3a310.movie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

	/**
	 * 動画を削除するとともに、紐づけられているコメントを削除する
	 * @param movie_id
	 */
	public void deleteOne(String movie_id) {
		/** 動画ファイルを取得する */
		String fileName = movieRepository.getFileName(movie_id);
		/** 動画ファイルを削除する */
		String pa = "/HCS_TUBE/src/main/resources/static/upload-dir/" + fileName;

		Path p = Paths.get(pa);

		/** コメントデータを削除する */
		movieRepository.deleteComment(movie_id);
		/** 動画データを削除する */
		movieRepository.deleteMovie(movie_id);
		/** 動画ファイルを削除する */
		try{
			  Files.delete(p);
			}catch(IOException e){
			  System.out.println(e);
			}
	}

	/*
	 * 検索機能
	 * @param category カテゴリー
	 * @param keyword キーワード
	 * @return userEntity
	 */
	public MovieEntity selectSearch(String keyword) {

		//エンティティクラスを作成
		MovieEntity movieEntity = new MovieEntity();
		try {
			movieEntity = movieRepository.selectMovieName(keyword);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return movieEntity;
	}

	/**
	 * 検索SQLを判断する
	 * @param keyword キーワード
	 * @return userEntity
	 */
	private MovieEntity cheakCategorySelect(String keyword) {
		//エンティティクラスを作成
		MovieEntity movieEntity = new MovieEntity();


		if(keyword.equals("movie_title")){
			movieEntity = movieRepository.selectMovieName(keyword);
		}else {
			System.out.println("失敗");


		}

		return movieEntity;
	}

}
