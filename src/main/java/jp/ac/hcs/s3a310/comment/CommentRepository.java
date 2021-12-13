package jp.ac.hcs.s3a310.comment;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository {

	/** SQL 全件取得（report_id昇順） */
	private static final String SQL_SELECT_ALL = "SELECT * FROM comments a INNER JOIN movies u ON a.movie_id = u.movie_id  order by houkoku_id";
	/** SQL 1件追加 */
	private static final String SQL_INSERT_ONE = "INSERT INTO comments(comment, user_id, movie_id, Registration_time) VALUES(?, ?, ?, ?)";
	/** SQL 1件削除 */
	private static final String SQL_DELETE_ONE = "DELETE FROM users WHERE comment = ?";

	@Autowired
	private JdbcTemplate jdbc;

	public CommentEntity selectAll() throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL);
		CommentEntity commentEntity = mappingSelectResult(resultList);
		return commentEntity;
	}

	private CommentEntity mappingSelectResult(List<Map<String, Object>> reportList) {
		CommentEntity entity = new CommentEntity();

		for (Map<String, Object> map : reportList) {
			CommentData data = new CommentData();
			data.setComment((String) map.get("comment"));
			data.setUser_id((String) map.get("user_id"));
			data.setMovie_id((int) map.get("movie_id"));
			data.setPost_time((Date) map.get("post_time"));


			entity.getCommentlist().add(data);
		}
		return entity;
	}

	public int insertOne(CommentData data) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_INSERT_ONE,
				data.getComment(),
				data.getUser_id(),
				data.getMovie_id(),
				data.getRegistration_time());

		return rowNumber;
	}

	public int deleteOne(String comment) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_DELETE_ONE, comment);
		return rowNumber;
	}
}