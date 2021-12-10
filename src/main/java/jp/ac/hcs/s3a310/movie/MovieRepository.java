package jp.ac.hcs.s3a310.movie;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MovieRepository {
	/** SQL 全件取得(投稿日時の降順) */
	private static final String SQL_SELECT_ALL_MOVIE = "SELECT * FROM movies ORDER BY post_time DESC";


	@Autowired
	private JdbcTemplate jdbc;

	public MovieEntity selectAll() {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL_MOVIE);
		MovieEntity movieEntity = mappingSelectResult(resultList);
		return movieEntity;
	}

	private MovieEntity mappingSelectResult(List<Map<String, Object>> resultList) {
		MovieEntity entity = new MovieEntity();

		for (Map<String, Object> map : resultList) {
			MovieData data = new MovieData();
			data.setMovie_id((int)map.get("movie_id"));
			data.setUser_id((String) map.get("user_id"));
			data.setPost_time(ChangeDate(map.get("post_time")));
			data.setMovie_title((String) map.get("movie_title"));
			data.setMovie_detail((String)map.get("movie_detail"));
			data.setFile_name((String)map.get("file_name"));
			data.setThumbnail((String)map.get("thumbnail"));;
			entity.getMovielist().add(data);
		}
		return entity;
	}

	/**
	 * Date型変換
	 * @param object 取得した申請内容
	 * @return date2
	 */
	private String ChangeDate(Object object) {

		String date2;

		if(!(object == null)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			date2 = format.format(object);
		} else {
			date2 = "";
		}
		return date2;
	}



}
