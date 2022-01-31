package jp.ac.hcs.s3a310.live;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class LiveService {
	@Autowired
	LiveRepository liveRepository = new LiveRepository();
	boolean flg =false;
	public boolean insertLive(Model model,Principal principal,LiveForm liveForm) {
		int live_id=liveRepository.getLiveIdMax();
		liveForm.setLive_id(live_id);
		liveForm.setUser_id(principal.getName());

		LiveData data= new LiveData();
		try {
		data=liveRepository.dataConversion(liveForm);

		flg=liveRepository.insertLive(data);
		}catch(DataAccessException e) {

		}
		return flg;
	}


}
