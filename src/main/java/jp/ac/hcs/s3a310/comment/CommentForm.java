package jp.ac.hcs.s3a310.comment;

import java.util.Date;

import lombok.Data;

@Data
public class CommentForm {

	/** ユーザID*/
//	@NotBlank(message = "{require_check}")
//	@Email(message = "{email_check}")
	private String user_id;

	/** 動画id */
//	@NotBlank(message = "{require_check}")
//	@Length(min = 4, max = 100, message = "{length_check}")
//	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{pattern_check}")
	private int movie_id;

	/** コメント */
//	@NotBlank(message = "{require_check}")
	private String comment;

//	/** 登録時間 */
//	@NotBlank(message = "{require_check}")
	private Date post_time;

}
