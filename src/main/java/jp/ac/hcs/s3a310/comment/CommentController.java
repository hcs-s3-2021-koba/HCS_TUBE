package jp.ac.hcs.s3a310.comment;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CommentController {

	@Autowired
	private CommentService commentService;

	/**
	 * コメントを表示する
	 * @param model 値の受け渡し
	 * @return 結果画面 - 就職活動申請報告一覧画面
	 */
	@GetMapping("/comment/commentList")
	public String getCommentList(Model model) {

		CommentEntity commentEntity = commentService.selectAll();
		model.addAttribute("commentEntity", commentEntity);
		return "/comment/commentList";
	}

	/**
	 *コメント新規登録する
	 * @param form 新規に登録するコメント内容
	 * @param bindingResult データバインド実施結果
	 * @param principal ログイン情報
	 * @param model 値の受け渡し
	 * @return 就職活動申請報告画面 - 就職活動申請報告一覧画面
	 */
	@PostMapping("/comment/insert")
	public String insertReport(@ModelAttribute @Validated CommentForm form,
			BindingResult bindingResult,
			Principal principal,
			Model model) {

		CommentData data = new CommentData();
		data.setComment(form.getComment());
		data.setUser_id(principal.getName());
		data.setMovie_id(form.getMovie_id());

		Date rd = new Date();
		SimpleDateFormat registration = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		String registration_time = registration.format(rd);
		data.setRegistration_time(registration_time);

		boolean result = commentService.insertOne(data);
		if (result) {
			log.info("[" + principal.getName() + "]コメント登録成功");
			model.addAttribute("result", "コメント登録成功");
		} else {
			log.warn("[" + principal.getName() + "]コメント登録失敗");
			model.addAttribute("result", "コメント登録失敗");
		}
		return getCommentList(model);
	}
}
