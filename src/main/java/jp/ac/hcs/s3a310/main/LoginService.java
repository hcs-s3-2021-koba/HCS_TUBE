package jp.ac.hcs.s3a310.main;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.ac.hcs.s3a310.user.UserData;

/**
 * ユーザ情報を操作する.
 */

@Transactional
@Service
public class LoginService {

	@Autowired
	jp.ac.hcs.s3a310.user.UserService userService;

	@Autowired
	HttpSession session;

	@Autowired
    PasswordEncoder passwordEncode;

	@Bean
    PasswordEncoder passwordEncode() {
        return new BCryptPasswordEncoder();
    }


	/**
	 * ログインしたユーザのユーザ情報を取得する.
	 * @param principal ログイン情報
	 * @return ログインユーザのユーザ情報
	 */
	public UserData getLoginUserInfo(Principal principal) {
		UserData userData = userService.selectOne(principal.getName());
		return userData;
	}

	/**
	 * 入力されたユーザIDとパスワードをもとにログイン処理を行う
	 * @param user_id
	 * @param password
	 * @return
	 */
	public boolean checkLogin(String user_id, String password) {

		return false;
	}

	/**
	 * パスワードの暗号化
	 */
	public String encryptionPassword(String password) {
		System.out.println(password);
		String enPass = passwordEncode.encode(password);
		return enPass;
	}



//	/**
//	 * ユーザ状態を確認し、ログイン可能か判定する
//	 * @param userData ユーザ情報
//	 * @return ログイン可能:true, ログイン不可:false
//	 */
//	public boolean judgeUserStatus(UserData userData) {
//
//		// ユーザロック判定
//		if (userData.getUser_status() > UserStatus.VALID.getCode()) {
//			log.info("[ユーザロック]ユーザ:" + userData.getUser_id()
//					+ ", user_status:" + userData.getUser_status()
//					+ ", password_error_count:" + userData.getPassword_error_count());
//
//			// ログイン画面のユーザロックメッセージを設定
//			session.setAttribute("UserLocked", "ユーザがロックまたは無効になっています。管理者に連絡してください。");
//			return false;
//		}
//
//		// パスワードエラー回数をリセット
//		userData.setPassword_error_count(0);
//		userService.updateOne(userData);
//
//		log.info("[ログイン成功]ユーザ:" + userData.getUser_id());
//
//		return true;
//	}

}
