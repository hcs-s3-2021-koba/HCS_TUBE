package jp.ac.hcs.s3a310.storage;

import java.io.IOException;
import java.security.Principal;

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
		 String path = "/HCS_TUBE/src/main/resources/static/upload-dir";

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
	public String handleFileUpload(@RequestParam("file") MultipartFile file,@RequestParam("movie_title") String title,@RequestParam("content") String content,
			 Principal principal , Model model ) {


		storageService.store(file,model);
		boolean flg =storageService.insertMovie(title, content, principal.getName() , file.getOriginalFilename());
		if(flg) {
			String msg="動画の投稿に成功しました";
			model.addAttribute("msg",msg);
		}else {
			String errMsg="動画の投稿に失敗しました";
			model.addAttribute("errMsg",errMsg);
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