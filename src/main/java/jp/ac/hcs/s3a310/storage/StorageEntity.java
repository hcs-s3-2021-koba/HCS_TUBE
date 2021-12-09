package jp.ac.hcs.s3a310.storage;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class StorageEntity {

	/** メッセージ*/
	private String msg;

	/** エラーメッセージ*/
	private String errMsg;

	/** 動画情報のリスト*/
	private List<StrageData> strageList= new ArrayList<StrageData>() ;
}