package jp.ac.hcs.s3a310.storage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StorageRepository {

	/** 動画IDの最小の値を取得する*/
	public final String SQL_SELECT_MOVIE_ID="";

	/** 動画テーブルに1件追加を行う*/
	public final String SQL_INSERT_MOVIE ="INSERT INTO movies(movie_id , user_id , post_time , movie_title , movie_detail)VALUES(?,?,?,?,?) ";

	/** 動画IDの最小値を求める*/
	public final String SQL_SELECT_MOVIE_ID_MIN ="SELECT MIN(movie_id) FROM movies ";







	@Autowired
	private JdbcTemplate jdbc;

	public String insertMovie(String user_id , String movie_title , String content) {

		Date dateObj = new Date();
		SimpleDateFormat format = new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" );
		String display = format.format( dateObj );
		int movie_id =0;
		int rowNumber = 0;

		List<Map<String, Object>> resultList=jdbc.queryForList(SQL_SELECT_MOVIE_ID_MIN );

		for(Map<String , Object> map : resultList) {
			 movie_id = (int)map.get("application_id") + 1;
		}

		rowNumber=jdbc.update(SQL_INSERT_MOVIE,movie_id , user_id , display , movie_title , content);

		//TODO エラーメッセージ　または成功メッセージを返す分岐を作る

		return String.valueOf(movie_id);
	}

}
