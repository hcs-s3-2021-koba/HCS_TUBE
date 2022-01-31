package jp.ac.hcs.s3a310.live;

import java.util.Date;

import lombok.Data;

@Data
public class LiveForm {

	/** ユーザID*/
//	@NotBlank(message = "{require_check}")
//	@Email(message = "{email_check}")
	private String user_id;

	/** ライブid */
//	@NotBlank(message = "{require_check}")
//	@Length(min = 4, max = 100, message = "{length_check}")
//	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{pattern_check}")
	private int live_id;

	/** ライブタイトル */
//	@NotBlank(message = "{require_check}")
	private String live_name;

	/** 開始予定時間 */
//	@NotBlank(message = "{require_check}")
	private Date start_time;


	/** 終了予定時間 */
//	@NotBlank(message = "{require_check}")
	private Date end_time;

//	/** ライブ中判別フラグ */
//	@NotBlank(message = "{require_check}")
	private int live_flg;

//	/** 概要 */
//	@NotBlank(message = "{require_check}")
	private String live_detail;


}
