package jp.ac.hcs.s3a310.movie;

import lombok.Data;

/**
 * アップデート用にパスワード、ダークモード、権限のチェックを外したUserForm.
 * その他の内容はUserFormに準じる.
 */
@Data
public class MovieFormForUpdate {

	/**
	 * 動画ID
	 */
	private int Movie_id;

	/** タイトル名 */
	private String movie_title;


	/** 概要欄 */
	private String movie_detail;

}
