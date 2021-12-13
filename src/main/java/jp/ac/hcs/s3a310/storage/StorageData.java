package jp.ac.hcs.s3a310.storage;

import java.util.Date;

import lombok.Data;

@Data
public class StorageData {


	/** 動画ID*/
	private int movie_id;

	/** ユーザID*/
	private String user_id;

	/** 投稿時間 */
	private Date post_time;

	/** 動画タイトル*/
	private String movie_title;

	/** 詳細情報*/
	private String movie_detail;

}
