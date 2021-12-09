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
	private static final String SQL_SELECT_ALL = "SELECT * FROM users order by user_id";

	/** SQL 1件取得 */
	private static final String SQL_SELECT_ONE = "SELECT * FROM users WHERE user_id = ?";

	/** SQL 1件追加 */
	private static final String SQL_INSERT_ONE = "INSERT INTO users(user_id, encrypted_password, user_name, user_authority,user_status) VALUES(?, ?, ?, ?, ?)";

	/** SQL 1件更新 管理者 パスワード更新有 */
	private static final String SQL_UPDATE_ONE_WITH_PASSWORD = "UPDATE users SET encrypted_password = ?,  user_name = ?, user_authority = ?, user_status = ? WHERE user_id = ?";

	/** SQL 1件更新 管理者 パスワード更新無 */
	private static final String SQL_UPDATE_ONE = "UPDATE users SET  user_name = ?, user_authority = ?, user_status = ? WHERE user_id = ?";

	/** SQL 1件削除 */
	private static final String SQL_DELETE_ONE = "DELETE FROM users WHERE user_id = ?";

	/** SQL 検索_No */
	private static final String SQL_SELECT_SEARCH_USER_ID = "SELECT * FROM users WHERE user_id LIKE ?";

	/** SQL 検索_NAME */
	private static final String SQL_SELECT_SEARCH_NAME = "SELECT * FROM users WHERE user_name LIKE ?";

	/** SQL 検索_権限 */
	private static final String SQL_SELECT_SEARCH_AUTHORITY = "SELECT * FROM users WHERE user_authority LIKE ?";

	/** SQL 検索_REPORT_STATE */
	private static final String SQL_SELECT_SEARCH_STATUS = "SELECT * FROM users WHERE user_status LIKE ?";

	/** SQL ユーザの状態を無効にする */
	private static final String SQL_USER_INVALID = "update users set user_status = false where user_id = ?";

	/** SQL ユーザの状態を有効にする */
	private static final String SQL_USER_VALID = "update users set user_status = true where user_id = ?";

	/** SQL ユーザの状態を取得する */
	private static final String SQL_SELECT_STATUS = "select user_status from users where user_id = ?";

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
			data.setUser_status((boolean) map.get("user_status"));

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
				data.getUser_authority(),
				data.isUser_status());
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
				userData.isUser_status(),
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
				userData.isUser_status(),
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

	public UserEntity selectSearchUserId(String keyword) {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_SEARCH_USER_ID,'%' +  keyword + '%');
		UserEntity userEntity = mappingSelectResult(resultList);
		return userEntity;
	}

	public UserEntity selectUserName(String keyword)throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_SEARCH_NAME, '%' +  keyword + '%');
		UserEntity userEntity = mappingSelectResult(resultList);
		return userEntity;
	}

	public UserEntity selectSearchAuthority(String keyword)throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_SEARCH_AUTHORITY, '%' +  keyword + '%');
		UserEntity userEntity = mappingSelectResult(resultList);
		return userEntity;
	}

	public UserEntity selectSearchSratus(boolean keyword)throws DataAccessException {
		List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_SEARCH_STATUS,keyword);
		UserEntity userEntity = mappingSelectResult(resultList);
		return userEntity;
	}

	/**
	 * ユーザの状態を無効にする
	 * @param name ユーザID
	 * @return rowNumber
	 */
	public int updateInvalid(String name) {
		int rowNumber = jdbc.update(SQL_USER_INVALID,name);
		return rowNumber;
	}

	/**
	 * ユーザの状態を有効にする
	 * @param name ユーザID
	 * @return rowNumber
	 */
	public int updateValid(String name) {
		int rowNumber = jdbc.update(SQL_USER_VALID,name);
		return rowNumber;
	}

	/**
	 * ユーザの状態を取得する
	 * @param user_id ユーザID
	 * @return s_flg
	 */
	public boolean selectStatus(String user_id) {
		boolean s_flg = jdbc.queryForObject(SQL_SELECT_STATUS,boolean.class,user_id);
		return s_flg;
	}


}

