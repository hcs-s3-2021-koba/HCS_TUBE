package jp.ac.hcs.s3a310;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	/** ユーザーIDとパスワードとアカウントの有効性を取得するSQL */
	private static final String USER_SQL = "SELECT user_id, encrypted_password as password, user_status FROM users WHERE user_id = ?";
	/** ユーザーIDと権限を取得するSQL */
	private static final String ROLE_SQL = "SELECT user_id, user_authority FROM users WHERE user_id = ?";

	@Bean
	public CorsConfigurationSource corsFilter() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);        // CORSリクエストでcookie情報の取得を許可するか
		configuration.setAllowedOrigins(Arrays.asList("*"));   // CORSリクエストを許可するドメイン
		configuration.setAllowedHeaders(Arrays.asList(  // CORSリクエストで受信を許可するヘッダー情報(以下は例です)
				"Access-Control-Allow-Headers",
				"Access-Control-Allow-Origin",
				"Access-Control-Request-Method",
				"Access-Control-Request-Headers",
				"Cache-Control",
				"Content-Type",
				"Accept-Language"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST"));  // CORSリクエストを許可するHTTPメソッド

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/cors", configuration); // CORSリクエストを許可するURLの形式(特に決まりがなければ「/**」でもOK)

		return source;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 静的リソースへのアクセスには、セキュリティを適用しない
		web.ignoring().antMatchers("/css/∗∗", "/h2-console/∗∗");
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// ログイン不要ページの設定
		http.authorizeRequests().antMatchers("/css/**").permitAll() // cssへアクセス許可
				.antMatchers("/img/**").permitAll() // imgへアクセス可能
				.antMatchers("/login").permitAll() // ログインページは直リンクOK
				.antMatchers("/user_login").permitAll() // ログインページは直リンクOK
				.antMatchers("/user_guest_login").permitAll() // ログインページは直リンクOK
				//.antMatchers("/top").permitAll() // ログインページは直リンクOK
				.antMatchers("/upload-dir/**").permitAll()//動画視聴は直リンクOK
				.antMatchers("/thumbnail/**").permitAll()//サムネイルは直リンクOK
				.antMatchers("/user/insert").permitAll() // 新規ユーザー登録画面は直リンクOK
				.antMatchers("/watcthMovie/**").permitAll() // 動画視聴画面は直リンクOK
				.antMatchers("/watch_live").permitAll() // ライブ視聴画面は直リンクOK
				.antMatchers("/user/reverse/**").hasAuthority("admin")// ユーザ管理機能は管理権限ユーザに許可
				.antMatchers("/watch_video/comment/insert").hasAuthority("admin")//コメント投稿はログインユーザのみ
				.antMatchers("/watch_video/comment/insert").hasAuthority("general")//コメント投稿はログインユーザのみ
				.anyRequest().authenticated(); // それ以外は直リンク禁止
		//ログイン処理
		http.formLogin().loginProcessingUrl("/login") // ログイン処理のパス
				.loginPage("/login") // ログインページの指定
				.failureUrl("/user_login") // ログイン失敗時の遷移先
				.usernameParameter("user_id") // ログインページのユーザID
				.passwordParameter("password") // ログインページのパスワード
				.defaultSuccessUrl("/top", true); // ログイン成功後の遷移先
		//ログアウト処理
		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutUrl("/logout") //ログアウト処理のパス
				.logoutSuccessUrl("/user_login"); //ログアウト成功後の遷移先
		// (開発用)CSRF対策 無効設定
		// XXX h2-console使用時は有効にする.
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// ログイン処理時のユーザ情報をDBから取得する
		auth.jdbcAuthentication()
				.dataSource(dataSource)
				.usersByUsernameQuery(USER_SQL)
				.authoritiesByUsernameQuery(ROLE_SQL)
				.passwordEncoder(passwordEncoder());
	}
}
