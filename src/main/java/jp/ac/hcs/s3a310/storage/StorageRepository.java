package jp.ac.hcs.s3a310.storage;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StorageRepository {

	/** 動画IDの最小の値を取得する*/
	public final String SQL_SELECT_MOVIE_ID="";

	/** 動画テーブルに1件追加を行う*/
	public final String SQL_INSERT_MOVIE ="INSERT INTO movies(movie_id , user_id , post_time , movie_title , movie_detail , file_name)VALUES(?,?,?,?,?,?) ";

	/** 動画IDの最小値を求める*/
	public final String SQL_SELECT_MOVIE_ID_MIN ="SELECT min(movie_id) AS movie_id FROM MOVIES";







	@Autowired
	private JdbcTemplate jdbc;

	public boolean insertMovie(String user_id , String movie_title , String content, String fileName)throws DataAccessException {

		Date dateObj = new Date();


		int movie_id =0;
		int rowNumber = 0;


		List<Map<String, Object>> resultList=jdbc.queryForList(SQL_SELECT_MOVIE_ID_MIN );

		for(Map<String , Object> map : resultList) {
			movie_id = (int)map.get("movie_id") + 1;
			rowNumber=jdbc.update(SQL_INSERT_MOVIE,movie_id , user_id , dateObj , movie_title , content , fileName);

		}
		return rowNumber > 0;

	}
}
