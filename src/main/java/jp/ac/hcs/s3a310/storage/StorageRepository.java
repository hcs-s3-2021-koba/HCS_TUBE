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
	public final String SQL_INSERT_MOVIE ="INSERT INTO movies(movie_id , user_id , post_time , movie_title , movie_detail , file_name,thumbnail)VALUES(?,?,?,?,?,?,?) ";

	/** 動画IDの最大を求める*/
	public final String SQL_SELECT_MOVIE_ID_MAX ="SELECT MAX(movie_id+1) AS movie_id FROM MOVIES";









	@Autowired
	private JdbcTemplate jdbc;

	public boolean insertMovie(String user_id , String movie_title , String content, String fileName, String thumbnail)throws DataAccessException {

		Date dateObj = new Date();


		int movie_id =1;
		int rowNumber = 0;

		try {
			List<Map<String, Object>> resultList=jdbc.queryForList(SQL_SELECT_MOVIE_ID_MAX);

			for(Map<String , Object> map : resultList) {
				Object dammy=map.get("movie_id");
				System.out.println(dammy);
				movie_id =Integer.parseInt(dammy.toString());

				rowNumber=jdbc.update(SQL_INSERT_MOVIE,movie_id , user_id , dateObj , movie_title , content , fileName , thumbnail);

			}
		}catch(Exception e) {
			/** 動画IDが存在しない（1つ目の動画投稿）の場合に遷移*/
			rowNumber=jdbc.update(SQL_INSERT_MOVIE,movie_id , user_id , dateObj , movie_title , content , fileName , thumbnail);


		}

		return rowNumber > 0;

	}
}
