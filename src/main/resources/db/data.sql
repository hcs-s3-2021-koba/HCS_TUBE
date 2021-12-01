/* 開発用にデータ削除を追加 : リリース時は消す */
DELETE FROM users;
DELETE FROM movies;
DELETE FROM comments;
DELETE FROM lives;

/* 管理者ユーザ */
INSERT INTO users (user_id, encrypted_password,	user_name,	user_authority,  user_status, registration_time)
VALUES('saito-san@hcs.ac.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa','MONSTER_SAITO', 'admin', 'valid', CURRENT_DATE);

/* 一般ユーザ */
INSERT INTO users (user_id, encrypted_password,	user_name,	user_authority,  user_status, registration_time)
VALUES('hashimoto@hcs.ac.jp', '$2a$10$xRTXvpMWly0oGiu65WZlm.3YL95LGVV2ASFjDhe6WF4.Qji1huIPa','まさるるる', 'general', 'valid', CURRENT_DATE);


/* 動画データ */
INSERT INTO movies (movie_id, user_id, post_time, movie_title, movie_time, movie_detail)
VALUES(1, 'saito-san@hcs.ac.jp','2021-12-02 12:00:00', 'SAITOとつくってあそぼ', '00:10:00', CURRENT_DATE);

/* コメントデータ */
INSERT INTO comments (post_time, user_id, movie_id, comment)
VALUES('2021-12-02 12:30:00', 'hashimoto@hcs.ac.jp', 1, 'とても面白かったです！チャンネル登録しました！');

/* ライブデータ */
INSERT INTO lives (live_id, live_name, user_id, start_time, end_time, live_detail)
VALUES(1, '初めまして、VHCSTuberのSAITOです！', 'saito-san@hcs.ac.jp', '2021-12-01 19:00:00', '2021-12-01 20:00:00', '初めまして！HCS所属の新人VHCSTuberのSAITOです！今日は皆さんからの質問にどんどん答えちゃいます！');

