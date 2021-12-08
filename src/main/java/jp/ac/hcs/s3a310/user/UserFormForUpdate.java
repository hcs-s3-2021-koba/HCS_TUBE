package jp.ac.hcs.s3a310.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * アップデート用にパスワード、ダークモード、権限のチェックを外したUserForm.
 * その他の内容はUserFormに準じる.
 */
@Data
public class UserFormForUpdate {

	/** ユーザID（メールアドレス）*/
	@NotBlank(message = "{require_check}")
	@Email(message = "{email_check}")
	private String user_id;

	/** パスワード */
	private String encrypted_password;


	/** ユーザ名 */
	@NotBlank(message = "{require_check}")
	private String user_name;

	/** 権限 */
	private String user_authority;

	/** ユーザ状態 */
	private boolean user_status;

}
