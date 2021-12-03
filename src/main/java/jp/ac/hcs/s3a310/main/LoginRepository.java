package jp.ac.hcs.s3a310.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepository {

	// --------------------- ユーザマスタ ---------------------

	/** SQL ログイン **/
	private static final String SQL_USER_LOGIN = "select * from users where user_id = ? & encrypted_password = ?";

	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	PasswordEncoder passwordEncoder;




}
