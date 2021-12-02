package jp.ac.hcs.s3a310.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

/**
 * ユーザ情報のデータを管理する.
 * - Userテーブル
 */
@Repository
public class UserRepository {

	/** SQL 全件取得（ユーザID昇順） */
	private static final String SQL_SELECT_ALL = "SELECT * FROM m_user order by user_id";

	/** SQL 1件取得 */
	private static final String SQL_SELECT_ONE = "SELECT * FROM m_user WHERE user_id = ?";

	/** SQL 1件追加 */
	private static final String SQL_INSERT_ONE = "INSERT INTO m_user(user_id, encrypted_password, class_number, user_name, darkmode, role) VALUES(?, ?, ?, ?, ?, ?)";

	/** SQL 1件更新 管理者 パスワード更新有 */
	private static final String SQL_UPDATE_ONE_WITH_PASSWORD = "UPDATE m_user SET encrypted_password = ?, class_number = ?, user_name = ?, role = ?, user_status = ?, password_error_count = ? WHERE user_id = ?";

	/** SQL 1件更新 管理者 パスワード更新無 */
	private static final String SQL_UPDATE_ONE = "UPDATE m_user SET class_number = ?, user_name = ?, role = ?, user_status = ?, password_error_count = ? WHERE user_id = ?";

	/** SQL 1件削除 */
	private static final String SQL_DELETE_ONE = "DELETE FROM m_user WHERE user_id = ?";

	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * Userテーブルから全データを取得.
	 * @return UserEntity
	 * @throws DataAccessException データアクセス時の例外をthrowする
	 */
	public UserEntity selectAll() throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ALL);
		UserEntity userEntity = mappingSelectResult(resultList);
		return userEntity;
	}

	/**
	 * Userテーブルから取得したデータをUserEntity形式にマッピングする.
	 * @param resultList Userテーブルから取得したデータ
	 * @return UserEntity
	 */
	private UserEntity mappingSelectResult(List<Map<String, Object>> resultList) {
		UserEntity entity = new UserEntity();

		for (Map<String, Object> map : resultList) {
			UserData data = new UserData();
			data.setUser_id((String) map.get("user_id"));
			data.setUser_name((String) map.get("user_name"));
			data.setUser_authority((String) map.get("user_authority"));
			data.setUser_status((int) map.get("user_status"));
			data.setRegistration_time((String) map.get("registration_time"));

			entity.getUserlist().add(data);
		}
		return entity;
	}

	/**
	 * Userテーブルにデータを1件追加する.
	 * @param data 追加するユーザ情報
	 * @return 追加データ数
	 * @throws DataAccessException データアクセス時の例外をthrowする
	 */
	public int insertOne(UserData data) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_INSERT_ONE,
				data.getUser_id(),
				passwordEncoder.encode(data.getEncrypted_password()),
				data.getUser_name(),
				data.getUser_authority());
		return rowNumber;
	}

	/**
	 * UserテーブルからユーザIDをキーにデータを1件を取得.
	 * @param user_id 検索するユーザID
	 * @return UserEntity
	 * @throws DataAccessException データアクセス時の例外をthrowする
	 */
	public UserData selectOne(String user_id) throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_ONE, user_id);
		UserEntity entity = mappingSelectResult(resultList);
		// 必ず1件のみのため、最初のUserDataを取り出す
		UserData data = entity.getUserlist().get(0);
		return data;
	}

	/**
	 * (管理用)Userテーブルのデータを1件更新する(パスワード更新有).
	 * @param userData 更新するユーザ情報
	 * @return 更新データ数
	 * @throws DataAccessException データアクセス時の例外をthrowする
	 */
	public int updateOneWithPassword(UserData userData) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_UPDATE_ONE_WITH_PASSWORD,
				passwordEncoder.encode(userData.getEncrypted_password()),
				userData.getUser_name(),
				userData.getUser_authority(),
				userData.getUser_status(),
				userData.getUser_id());
		return rowNumber;
	}

	/**
	 * (管理用)Userテーブルのデータを1件更新する(パスワード更新無).
	 * @param userData 更新するユーザ情報
	 * @return 更新データ数
	 * @throws DataAccessException データアクセス時の例外をthrowする
	 */
	public int updateOne(UserData userData) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_UPDATE_ONE,
				userData.getUser_name(),
				userData.getUser_authority(),
				userData.getUser_status(),
				userData.getUser_id());
		return rowNumber;
	}

	/**
	 * Userテーブルのデータを1件削除する.
	 * @param user_id 削除するユーザID
	 * @return 削除データ数
	 * @throws DataAccessException データアクセス時の例外をthrowする
	 */
	public int deleteOne(String user_id) throws DataAccessException {
		int rowNumber = jdbc.update(SQL_DELETE_ONE, user_id);
		return rowNumber;
	}

}

