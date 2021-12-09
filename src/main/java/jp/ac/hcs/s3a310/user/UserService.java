package jp.ac.hcs.s3a310.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ユーザ情報を操作する.
 */
@Transactional
@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	/**
	 * ユーザ情報を全件取得する.
	 * @return UserEntity
	 */
	public UserEntity selectAll() {
		return userRepository.selectAll();
	}

	/**
	 * ユーザ情報を1件追加する.
	 * @param userData 追加するユーザ情報(パスワードは平文)
	 * @return 処理結果(成功:true, 失敗:false)
	 */
	public boolean insertOne(UserData userData) {
		int rowNumber = userRepository.insertOne(userData);
		boolean result = (rowNumber > 0) ? true : false;
		return result;
	}

	/**
	 * 指定したユーザIDのユーザ情報を取得する.
	 * @param user_id ユーザID
	 * @return UserData
	 */
	public UserData selectOne(String user_id) {
		return userRepository.selectOne(user_id);
	}

	/**
	 * (管理用)ユーザ情報を1件更新する(パスワード更新有).
	 * @param userData 更新するユーザ情報(パスワードは平文)
	 * @return 処理結果(成功:true, 失敗:false)
	 */
	public boolean updateOneWithPassword(UserData userData) {
		int rowNumber = userRepository.updateOneWithPassword(userData);
		boolean result = (rowNumber > 0) ? true : false;
		return result;
	}

	/**
	 * (管理用)ユーザ情報を1件更新する(パスワード更新無).
	 * @param userData 更新するユーザ情報(パスワードは設定しない)
	 * @return 処理結果(成功:true, 失敗:false)
	 */
	public boolean updateOne(UserData userData) {
		int rowNumber = userRepository.updateOne(userData);
		boolean result = (rowNumber > 0) ? true : false;
		return result;
	}

	/**
	 * ユーザ情報を1件削除する.
	 * @param user_id 削除したいユーザID
	 * @return 処理結果(成功:true, 失敗:false)
	 */
	public boolean deleteOne(String user_id) {
		int rowNumber = userRepository.deleteOne(user_id);
		boolean result = (rowNumber > 0) ? true : false;
		return result;
	}

	/**
	 * 検索機能
	 * @param category カテゴリー
	 * @param keyword キーワード
	 * @return userEntity
	 */
	public UserEntity selectSearch(String category, String keyword) {

		//エンティティクラスを作成
		UserEntity userEntity = new UserEntity();
		try {
			userEntity = cheakCategorySelect(category,keyword);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}

		return userEntity;
	}

	/**
	 * 検索SQLを判断する
	 * @param category 項目
	 * @param keyword キーワード
	 * @return userEntity
	 */
	private UserEntity cheakCategorySelect(String category, String keyword) {
		//エンティティクラスを作成
		UserEntity userEntity = new UserEntity();


		if(category.equals("user_id")){
			userEntity = userRepository.selectSearchUserId(keyword);
		}else if(category.equals("user_name")) {
			userEntity = userRepository.selectUserName(keyword);
		}else if(category.equals("user_authority")) {
			if(keyword.equals("一般")||keyword.equals("general")) {
				userEntity = userRepository.selectSearchAuthority("general");
			}else if(keyword.equals("管理者")||keyword.equals("admin")) {
				userEntity = userRepository.selectSearchAuthority("admin");
			}else {
				userEntity = userRepository.selectSearchAuthority(keyword);
			}
		}else if(category.equals("user_status")) {
			if(keyword.equals("有効")||keyword.equals("true")) {
				userEntity = userRepository.selectSearchSratus(true);
			}else if(keyword.equals("無効")||keyword.equals("false")) {
				userEntity = userRepository.selectSearchSratus(false);
			}

		}

		return userEntity;
	}

	/**
	 * ユーザのステータスを取得
	 * @param user_id ユーザID
	 * @return s_flg
	 */
	public boolean getStatus(String user_id) {
		boolean s_flg = userRepository.selectStatus(user_id);
		return s_flg;
	}


	/**
	 * ユーザのステータスを反転する
	 * @param status_flg 状態フラグ
	 * @param user_id ユーザID
	 * @return rowNumber
	 */
	public int reverseStatus(boolean status_flg, String user_id) {
		int rowNumber;
		if(status_flg) {
			rowNumber = userRepository.updateInvalid(user_id);
		} else {
			rowNumber = userRepository.updateValid(user_id);
		}
		return rowNumber;
	}

}
