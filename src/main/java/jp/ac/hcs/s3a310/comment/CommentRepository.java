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
	private static final String SQL_SELECT_ALL = "SELECT * FROM comments a INNER JOIN movies u ON a.movie_id = u.movie_id  order by movie_id";
	/** SQL 1件追加 */
	private static final String SQL_INSERT_ONE = "INSERT INTO comments(comment_content, user_id, movie_id, post_time) VALUES(?, ?, ?,sysdate)";
	/** SQL 1件削除 */
	private static final String SQL_DELETE_ONE = "DELETE FROM users WHERE comment_content = ?";
	/** SQL movie_idで全件取得(post_timeの降順) */
	private static final String SQL_SELECT_COMMENT_ALL = "SELECT c.user_id, u.user_name, c.comment_content, c.movie_id, c.post_time FROM comments c INNER JOIN users u ON c.user_id = u.user_id WHERE c.movie_id = ? ORDER BY c.post_time DESC";

	@Autowired
	private JdbcTemplate jdbc;

	public CommentEntity selectAll() throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL);
		CommentEntity commentEntity = mappingSelectResult(resultList);
		return commentEntity;
	}

	public CommentEntity selectComment(String movie_id) {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_COMMENT_ALL, movie_id);
		CommentEntity commentEntity = mappingSelectResult(resultList);
		return commentEntity;
	}

	private CommentEntity mappingSelectResult(List<Map<String, Object>> reportList) {
		CommentEntity entity = new CommentEntity();

		for (Map<String, Object> map : reportList) {
			/** BigDecimal→String→int変換 */
			String wk = map.get("movie_id").toString();
			int id =Integer.valueOf(wk);

			CommentData data = new CommentData();
			data.setComment_content((String) map.get("comment_content"));
			data.setUser_id((String) map.get("user_id"));
			data.setUser_name((String) map.get("user_name"));
			data.setMovie_id((int) id);
			data.setPost_time((Date) map.get("post_time"));

			entity.getCommentlist().add(data);
		}
		return entity;
	}

	public int insertOne(CommentData data) throws DataAccessException {

		int rowNumber = jdbc.update(SQL_INSERT_ONE,
				data.getComment_content(),
				data.getUser_id(),
				data.getMovie_id()
				);

		return rowNumber;
	}

	public int deleteOne(String comment) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_DELETE_ONE, comment);
		return rowNumber;
	}


}