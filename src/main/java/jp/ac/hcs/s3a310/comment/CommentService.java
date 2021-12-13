package jp.ac.hcs.s3a310.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
@Service
public class CommentService {
	@Autowired
	CommentRepository commentRepository;

	/**
	 * 就職活動申請報告一覧を取得する
	 * @return HoukokuEntity
	 */
	public CommentEntity selectAll() {
		return commentRepository.selectAll();
	}

	/**
	 * 就職活動申請報告を一件登録する
	 * @param data 登録する報告内容
	 * @return 登録された件数
	 */
	public boolean insertOne(CommentData data) {
		int rowNumber;
		try {
			rowNumber = commentRepository.insertOne(data);
		} catch (DataAccessException e) {
			e.printStackTrace();
			rowNumber = 0;
		}
		return rowNumber > 0;
	}
	/**
	 * 就職活動申請情報を1件削除する.
	 * @param data 削除したい就職活動申請内容
	 * @return 処理結果(成功:true, 失敗:false)
	 */
	public boolean deleteOne(String comment) {
		int rowNumber = commentRepository.deleteOne(comment);
		boolean result = (rowNumber > 0) ? true : false;
		return result;
	}
}
