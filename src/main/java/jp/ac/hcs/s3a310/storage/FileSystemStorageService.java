package jp.ac.hcs.s3a310.storage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	@Autowired
	StorageRepository storageRepository = new StorageRepository();

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void store(MultipartFile file , Model model) {

		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
			}
			/** ファイルの拡張子を抽出し、形式を確認する*/
			String name=file.getOriginalFilename();
			String[] data = name.split("\\.");

			if(data[data.length-1].equals("mp4") || data[data.length-1].equals("m4a")) {
			Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
			}
		} catch (IOException e) {
			String errMsg = "すでに同じ名前のファイルが存在します。";
			model.addAttribute(errMsg,"errMsg");
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
					.filter(path -> !path.equals(this.rootLocation))
					.map(path -> this.rootLocation.relativize(path));
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if(resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}
	@Override
	public Boolean delete(String filename){
		Path p = Paths.get("/HCS_TUBE/up/"+filename);
		boolean flg =false;
		try{
			  Files.delete(p);
			  flg=true;
			}catch(IOException e){
			  flg=false;
			}

		return flg;

	}

	@Override
	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	@Override
	public Boolean insertMovie(String title , String content ,String user_id ,String fileName) {
		String msg = "";
		boolean flg=true;
		try {
		flg = storageRepository.insertMovie(user_id, title, content , fileName);
		}catch(DataAccessException e ) {
			flg=false;

		}



		return flg;
	}

}
