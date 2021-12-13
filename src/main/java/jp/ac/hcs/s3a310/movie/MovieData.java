package jp.ac.hcs.s3a310.movie;

import lombok.Data;

@Data
public class MovieData {
	/**
	 * 動画のID
	 * 1から始まる連番
	 */
	private int movie_id;

	/**
	 * ユーザのID
	 * ユーザマスタの主キーを外部キーとして参照
	 */
	private String user_id;

	/**
	 * 動画を投稿した日時
	 * YYYYMMDDHHMMSS
	 */
	private String post_time;

	/**
	 * 動画のタイトル
	 */
	private String movie_title;

	/**
	 * 動画の説明文
	 */
	private String movie_detail;

	/**
	 * 動画ファイルのファイル名
	 */
	private String file_name;

	/**
	 * サムネイルのファイル名
	 */
	private String thumbnail;
}
