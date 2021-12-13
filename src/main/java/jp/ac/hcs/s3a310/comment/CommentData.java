package jp.ac.hcs.s3a310.comment;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 1件分の就職活動報告情報.
 * 各項目のデータ仕様も合わせて管理する.
 */
@Data
@NoArgsConstructor
public class CommentData {

	/**
	 * ユーザID
	 */
	private String user_id;

	/**
	 * コメントID
	 */
	private String comment;

	/**
	 * 	動画ID
	 */
	private int movie_id;

	/**
	 * 登録日時
	 */
	private Date post_time;

	private String registration_time;


}
