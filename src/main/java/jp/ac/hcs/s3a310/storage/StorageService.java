package jp.ac.hcs.s3a310.storage;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void init();

	void store(MultipartFile file);

	/** 読み込んできたfileをStreamでPathに全部まとめる*/
	Stream<Path> loadAll();

	/** まとめたPathにfilename（アップロードしたファイル名）のみを抽出*/
	Path load(String filename);

	/** 動画情報を一件追加する*/
	String insertMovie(String title , String content , String user_id , String fileName);

	/** 動画ファイルの読み込みを行う*/
	Resource loadAsResource(String filename);


	/** 動画ファイルの削除を行う。*/
	void deleteAll();

}