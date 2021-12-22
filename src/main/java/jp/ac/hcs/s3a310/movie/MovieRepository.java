package jp.ac.hcs.s3a310.movie;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MovieRepository {

	/** SQL 全件取得(投稿日時の降順) */
	private static final String SQL_SELECT_ALL_MOVIE = "SELECT * FROM movies ORDER BY post_time DESC";

	/** SQL 1件取得(movie_id) */
	private static final String SQL_SELECT_ONE_MOVIE = "SELECT * FROM movies WHERE movie_id = ?";

	/** SQL 1件更新 */
	private static final String SQL_UPDATE_ONE = "UPDATE movies SET  movie_title = ?, movie_detail = ? WHERE movie_id = ?";

	/** ファイル名取得 */
	private static final String SQL_SELECT_FILE = "SELECT file_name FROM movies WHERE movie_id = ?";

	/** SQL コメント削除(movie_id) */
	private static final String SQL_DELETE_COMMENT = "DELETE FROM comments WHERE movie_id = ?";

	/** SQL 動画削除(movie_id) */
	private static final String SQL_DELETE_MOVIE = "DELETE FROM movies WHERE movie_id = ?";

	/** タイトル検索 */
	private static final String SQL_SELECT_SEARCH_NAME = "SELECT * FROM movies WHERE movie_title LIKE ? ORDER BY post_time DESC";

	@Autowired
	private JdbcTemplate jdbc;

	/**
	 * 動画情報全件取得
	 * @return movieEntity
	 */
	public MovieEntity selectAll() {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL_MOVIE);
		MovieEntity movieEntity = mappingSelectResult(resultList);
		return movieEntity;
	}


	/**
	 * 動画情報1件取得
	 * @param movie_id
	 * @return movieData
	 */
	public MovieData selectOne(String movie_id) {
		int id = Integer.parseInt(movie_id);
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ONE_MOVIE, movie_id);
		MovieEntity movieEntity = mappingSelectResult(resultList);
		MovieData movieData = movieEntity.getMovielist().get(0);
		return movieData;
	}

	private MovieEntity mappingSelectResult(List<Map<String, Object>> resultList) {
		MovieEntity entity = new MovieEntity();

		for (Map<String, Object> map : resultList) {

			String wk = map.get("movie_id").toString();
			int id =Integer.valueOf(wk);

			MovieData data = new MovieData();
			data.setMovie_id((int)id);
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

	/**
	 * (管理用)Userテーブルのデータを1件更新する(パスワード更新無).
	 * @param userData 更新するユーザ情報
	 * @return 更新データ数
	 * @throws DataAccessException データアクセス時の例外をthrowする
	 */
	public int updateOne(MovieData movieData) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_UPDATE_ONE,
				movieData.getMovie_title(),
				movieData.getMovie_detail(),
				movieData.getMovie_id());
		return rowNumber;
	}

	public void deleteComment(String movie_id) {
		jdbc.update(SQL_DELETE_COMMENT, movie_id);
	}

	public void deleteMovie(String movie_id) {
		jdbc.update(SQL_DELETE_MOVIE, movie_id);
	}


	public String getFileName(String movie_id) {
		String fileName = jdbc.queryForObject(SQL_SELECT_FILE, String.class, movie_id);
		return fileName;
	}

	/**
	 * 検索 動画タイトル
	 * @param keyword
	 * @return
	 * @throws DataAccessException
	 */
	public MovieEntity selectMovieName(String keyword)throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_SEARCH_NAME, '%' +  keyword + '%');
		MovieEntity movieEntity = mappingSelectResult(resultList);
		return movieEntity;
	}







}
