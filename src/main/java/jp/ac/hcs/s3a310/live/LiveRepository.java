package jp.ac.hcs.s3a310.live;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LiveRepository {
	//** ライブが始まっているライブのみをセレクトする*/
	public static final String SQL_SELECT_LIVE ="select * from lives where live_flg=1";

	//** 新しいライブを作成*/
	public static final String SQL_INSERT_LIVE="INSERT INTO lives(live_id,live_name,user_id,live_detail,live_flg) VALUES(?,?,?,?,?) ";

	//** ライブを削除する*/
	public static final String SQL_DELETE_LIVE ="delete lives where live_id=?";

	//** ライブIDを取得する*/
	public static final String SQL_SELECT_LIVE_ID="select MAX(live_id) from lives";



	@Autowired
	private JdbcTemplate jdbc;



	public int getLiveIdMax() {
		int live_id=0;

		try {
			List<Map<String, Object>> resultList = jdbc.queryForList(SQL_SELECT_LIVE_ID);
			LiveEntity liveEntity = mappingSelectResult(resultList);

		}catch(Exception e) {
			live_id=0;
		}


		return live_id;
	}

	private LiveEntity mappingSelectResult(List<Map<String, Object>> liveList) {
		LiveEntity entity = new LiveEntity();

		for (Map<String, Object> map : liveList) {
			/** BigDecimal→String→int変換 */
			LiveData data = new LiveData();
			data.setLive_id((int) map.get("live_id"));
			data.setUser_id((String) map.get("user_id"));
			data.setLive_name((String) map.get("live_name"));
			data.setLive_flg((int)map.get("live_flg"));


			entity.getLiveList().add(data);
		}
		return entity;
	}


	public boolean insertLive(LiveData data)throws DataAccessException{
		System.out.println(data);

		int rowNumber = jdbc.update(SQL_INSERT_LIVE,
				data.getLive_id(),
				data.getLive_name(),
				data.getUser_id(),
				data.getLive_detail(),
				data.getLive_flg()
				);
		System.out.println(rowNumber);



		return rowNumber>0;
	}


	public boolean deleteLive(int liveId) {


		return true;
	}


	public LiveData dataConversion(LiveForm form) {
		LiveData data =new LiveData();
		data.setLive_id(form.getLive_id());
		data.setLive_name(form.getLive_name());
		data.setUser_id(form.getUser_id());
		data.setLive_detail(form.getLive_detail());
		data.setLive_flg(form.getLive_flg());


		return data;
	}

}
