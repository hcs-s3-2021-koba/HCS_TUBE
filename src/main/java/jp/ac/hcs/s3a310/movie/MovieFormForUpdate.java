package jp.ac.hcs.s3a310.movie;

import javax.validation.constraints.NotBlank;

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
	@NotBlank(message = "タイトルを入力してください")
	private String movie_title;


	/** 概要欄 */
	@NotBlank(message = "概要を入力してください")
	private String movie_detail;

}
