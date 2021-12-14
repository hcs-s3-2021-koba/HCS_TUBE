package jp.ac.hcs.s3a310.user;


import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * フロント-バックでユーザ情報をやり取りする.
 * 各項目のデータ仕様はUserEntityを参照とする.
 * 本クラスを修正する場合は、UserFormForUpdateも合わせて修正するものとする.
 */
@Data
public class UserForm {

	/** ユーザID（メールアドレス）*/
//	@NotBlank(message = "{require_check}")
	@Email(message = "アドレス形式で入力してください")
	private String user_id;

	/** パスワード */
	@Length(min = 8, max = 16, message = "8桁以上、16桁以下の桁数で入力してください")
	@Pattern(regexp = "^\\w+$", message = "半角英数字を入力してください")
	private String encrypted_password;

	/** ユーザ名 */
//	@NotBlank(message = "{require_check}")
	private String user_name;

//	/** 権限 */
//	@NotBlank(message = "{require_check}")
//	private String user_authority;

}
