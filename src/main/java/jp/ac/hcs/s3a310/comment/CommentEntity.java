package jp.ac.hcs.s3a310.comment;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CommentEntity {

	/** 動画情報のリスト */
	private List<CommentData> commentlist = new ArrayList<CommentData>();

}