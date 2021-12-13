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
	@Email(message = "{email_check}")
	private String user_id;

	/** パスワード */
	@Length(min = 8, max = 16, message = "{length_check}")
	@Pattern(regexp = "^\\w+$", message = "{pattern_check}")
	private String encrypted_password;

	/** ユーザ名 */
//	@NotBlank(message = "{require_check}")
	private String user_name;

//	/** 権限 */
//	@NotBlank(message = "{require_check}")
//	private String user_authority;

}
