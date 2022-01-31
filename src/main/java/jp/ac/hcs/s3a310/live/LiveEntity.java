package jp.ac.hcs.s3a310.live;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class LiveEntity {

	/** 動画情報のリスト */
	private List<LiveData> liveList = new ArrayList<LiveData>();

}