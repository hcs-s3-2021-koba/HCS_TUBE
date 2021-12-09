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

	String insertMovie(String title , String content , String user_id , String fileName);

	Resource loadAsResource(String filename);

	void deleteAll();

}