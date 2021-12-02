package jp.ac.hcs.s3a310.user;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 1件分のユーザ情報.
 * 各項目のデータ仕様も合わせて管理する.
 */
@Data
@NoArgsConstructor
public class UserData {

	/**
	 * ユーザID（メールアドレス）
	 * 主キー、必須入力、メールアドレス形式
	 */
	private String user_id;

	/**
	 * 暗号化パスワード
	 * 必須入力、長さ4から100桁まで、半角英数字のみ
	 */
	private String encrypted_password;

	/**
	 * ユーザ名
	 * 必須入力
	 */
	private String user_name;

	/**
	 * ユーザ権限
	* general：一般ユーザ
	* admin：管理ユーザ
	 */
	private String user_authority;

	/**
	 * ユーザ状態
	 * - UserStatusクラスで定義
	 */
	private int user_status;

	/**
	 * 登録日時
	 * ユーザ作成時は0固定
	 * ユーザ状態を有効にした際に0に戻す
	 */
	private String registration_time;

}
