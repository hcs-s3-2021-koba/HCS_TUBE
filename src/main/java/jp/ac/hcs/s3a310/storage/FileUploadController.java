package jp.ac.hcs.s3a310.storage;

import java.io.File;
import java.io.IOException;
import java.security.Principal;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jp.ac.hcs.s3a310.ftp.Ftp;
import jp.ac.hcs.s3a310.ftp.Ssh;
import jp.ac.hcs.s3a310.movie.MovieEntity;
import jp.ac.hcs.s3a310.movie.MovieService;

@Controller
public class FileUploadController {

	private final StorageService storageService;

	@Autowired
	MovieService movieService;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/files")
	public String listUploadedFiles(Model model,Principal principal) throws IOException {
		String path = "/HCS_TUBE/up/";


		model.addAttribute("files",path);
		//		model.addAttribute("files",
		//				storageService.loadAll().map(
		//				path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
		//						"serveFile", path.getFileName().toString()).build().toUri().toString())
		//				.collect(Collectors.toList()));

		return "movie/upload";
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {



		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("/files")
	public String handleFileUpload(@RequestParam("file") MultipartFile multiFile,@RequestParam("movie_title") String title,@RequestParam("content") String content,
			Principal principal , Model model ) throws IOException {




		storageService.store(multiFile,model);
		boolean flg =storageService.insertMovie(title, content, principal.getName() , multiFile.getOriginalFilename(),multiFile.getOriginalFilename());
		int movie_id=storageService.getMovieId();

		if(flg) {
			String msg="????????????????????????????????????";
			model.addAttribute("msg",msg);
		}else {
			String errMsg="????????????????????????????????????";
			model.addAttribute("errMsg",errMsg);
		}






		try {

			File file = new File("/tmp",multiFile.getOriginalFilename());
			FileUtils.writeByteArrayToFile(file , multiFile.getBytes());
			//?????????????????????????????????????????????????????????
			Ftp ftpp = new Ftp(file.getAbsolutePath(),String.valueOf(movie_id));
			Ssh ss = new Ssh();
			try {
				flg =ftpp.connect();
				flg=ftpp.put(file);

				flg=ftpp.disconnect();
				ss.sh(movie_id+" "+multiFile.getOriginalFilename());
				file.delete();
			} catch (Exception e) {

			}
		}catch(Exception e) {


		}
		MovieEntity movieEntity = movieService.selectAll();
		model.addAttribute("movieEntity", movieEntity);
		return "/top";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}