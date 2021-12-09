package jp.ac.hcs.s3a310.storage;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class StorageFrom {

	//TODO @系の制約をつける

	private MultipartFile file;

	private String movie_title;

	private String movie_detail;





}
