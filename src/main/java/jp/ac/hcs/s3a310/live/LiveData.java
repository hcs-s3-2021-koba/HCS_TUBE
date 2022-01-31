package jp.ac.hcs.s3a310.live;

import java.util.Date;

import lombok.Data;

@Data
public class LiveData {
	/** ユーザID*/

	private String user_id;

	/** ライブID */

	private int live_id;

	/** ライブタイトル */

	private String live_name;

	/** 開始予定時間 */

	private Date start_time;


	/** 終了予定時間 */

	private Date end_time;

	/** ライブ中判別フラグ*/
	private int live_flg;


	/** 概要*/
	private String live_detail;


}
