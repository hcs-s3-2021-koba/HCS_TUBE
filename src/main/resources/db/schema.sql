/* ユーザマスタ */
CREATE TABLE IF NOT EXISTS users (
	user_id VARCHAR(254),
	encrypted_password VARCHAR(100) NOT NULL,
	user_name VARCHAR(60) NOT NULL,
	user_authority VARCHAR(10) NOT NULL,
	user_status VARCHAR(10) NOT NULL,
	registration_time DATETIME NOT NULL,
	PRIMARY KEY (user_id)
);

/* 動画マスタ */
CREATE TABLE IF NOT EXISTS movies (
	movie_id INT(254),
	user_id VARCHAR(254) ,
	post_time DATETIME NOT NULL,
	movie_title VARCHAR(254) NOT NULL,
	movie_time TIME NOT NULL,
	movie_detail VARCHAR(254),
	PRIMARY KEY (movie_id),
	FOREIGN KEY (user_id) REFERENCES users(user_id)
);

/* コメントマスタ */
CREATE TABLE IF NOT EXISTS comments (
	post_time DATETIME,
	user_id VARCHAR(254),
	movie_id INT(254),
	comment VARCHAR(254) NOT NULL,
	PRIMARY KEY (post_time,user_id,movie_id),
	FOREIGN KEY (user_id) REFERENCES users(user_id),
	FOREIGN KEY (movie_id) REFERENCES movies(movie_id)
);

/* ライブマスタ */
CREATE TABLE IF NOT EXISTS lives (
	live_id INT(254),
	live_name VARCHAR(254) NOT NULL,
	user_id VARCHAR(254) NOT NULL,
	start_time DATETIME NOT NULL,
	end_time DATETIME NOT NULL,
	live_detail VARCHAR(254),
	PRIMARY KEY (live_id),
	FOREIGN KEY (user_id) REFERENCES users(user_id)
);