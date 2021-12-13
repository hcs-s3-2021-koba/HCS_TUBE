package jp.ac.hcs.s3a310.movie;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MovieEntity {

	/** 動画情報のリスト */
	private List<MovieData> movielist = new ArrayList<MovieData>();

}
