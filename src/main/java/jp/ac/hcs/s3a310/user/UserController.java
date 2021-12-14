package jp.ac.hcs.s3a310.user;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

/**
 * ユーザ情報に関する機能・画面を制御する.
 */
@Slf4j
@Controller
public class UserController {

	@Autowired
	UserService userService;

	/** ラジオボタン用変数 */
	private Map<String, String> radioRole;
	private Map<String, String> userStatus;

	/** 権限のラジオボタンを初期化する処理 */
	private Map<String, String> initRadioRole() {
		Map<String, String> radio = new LinkedHashMap<>();
		radio.put("一般", "GENERAL");
		radio.put("管理", "ADMIN");
		return radio;
	}

	/** ユーザ状態のラジオボタンを初期化する処理 */
	private Map<String, String> initUserStatus() {
		Map<String, String> radio = new LinkedHashMap<>();
		radio.put("一般", String.valueOf(UserStatus.GENERAL.getCode()));
		// ロック中はユーザは個別に設定できない
		radio.put("管理者", String.valueOf(UserStatus.ADMIN.getCode()));
		return radio;
	}

	/** ユーザ状態の表示を文字で行うための処理 */
	private Map<Integer, String> viewUserStatus() {
		Map<Integer, String> viewString = new LinkedHashMap<>();
		viewString.put(UserStatus.GENERAL.getCode(), "一般");
		viewString.put(UserStatus.ADMIN.getCode(), "管理");
		return viewString;
	}

	/**
	 * ユーザ一覧画面を表示する.
	 * @param model 値を受け渡す
	 * @return ユーザ一覧画面
	 */
	@GetMapping("/user_setting")
	public String getUserList(Model model) {

		// ユーザ状態の日本語表示の準備
		model.addAttribute("viewUserStatus", viewUserStatus());

		UserEntity userEntity = userService.selectAll();
		model.addAttribute("userEntity", userEntity);

		return "user/user_setting_home";
	}

	/**
	 * ユーザ登録画面（管理者用）を表示する.
	 * @param form 登録時の入力チェック用UserForm
	 * @param model 値を受け渡す
	 * @return ユーザ登録画面（管理者用）
	 */
	@GetMapping("/user/insert")
	public String getUserInsert(@ModelAttribute UserForm form, Model model) {
		return "user/newuser";
	}

	/**
	 * 1件分のユーザ情報をデータベースに登録する.
	 * @param form 登録するユーザ情報(パスワードは平文)
	 * @param bindingResult データバインド実施結果
	 * @param principal ログイン情報
	 * @param model 値を受け渡す
	 * @return ユーザ一覧画面
	 */
	@PostMapping("/user/insert")
	public String postUserInsert(@ModelAttribute @Validated UserForm form
		,BindingResult bindingResult,
		Model model
			) {

		// 入力チェックに引っかかった場合、前の画面に戻る
		if (bindingResult.hasErrors()) {
			return getUserInsert(form, model);
		}

//		log.info("[" + principal.getName() + "]ユーザ登録データ:" + form.toString());
		System.out.println(form);

		String user_authority = "general";

		UserData data = new UserData();
		data.setUser_id(form.getUser_id());
		data.setEncrypted_password(form.getEncrypted_password());
		data.setUser_name(form.getUser_name());
		data.setUser_authority(user_authority);
		data.setUser_status(true);

		boolean result = userService.insertOne(data);

//		if (result) {
//			log.info("[" + principal.getName() + "]ユーザ登録成功");
//			model.addAttribute("result", "ユーザ登録成功");
//		} else {
//			log.warn("[" + principal.getName() + "]ユーザ登録失敗");
//			model.addAttribute("result", "ユーザ登録失敗");
//		}

//		return getUserList(model)
		return "user/login";
	}
	/**
	 * ユーザ詳細情報画面を表示する.
	 * @param form 更新時の入力チェック用UserForm
	 * @param user_id 検索するユーザID
	 * @param principal ログイン情報
	 * @param model 値を受け渡す
	 * @return ユーザ詳細情報画面
	 */
	@GetMapping("/user/detail/{id:.+}")
	public String getUserDetail(@ModelAttribute UserFormForUpdate form,
			@PathVariable("id") String user_id,
			Principal principal,
			Model model) {

//		// ラジオボタンの準備
//		radioRole = initRadioRole();
//		model.addAttribute("radioRole", radioRole);
//		userStatus = initUserStatus();
//		model.addAttribute("userStatus", userStatus);

		// 検索するユーザーIDのチェック
		if (user_id != null && user_id.length() > 0) {

			log.info("[" + principal.getName() + "]ユーザ検索:" + user_id);

			UserData data = userService.selectOne(user_id);

			// データ表示準備(パスワードは暗号化済の為、表示しない)
			form.setUser_id(data.getUser_id());
			form.setUser_name(data.getUser_name());
			form.setUser_authority(data.getUser_authority());
			form.setUser_status(data.isUser_status());
			model.addAttribute("userFormForUpdate", form);
		}

		return "user/user_detail";
	}

	/**
	 * ユーザ登録画面（管理者用）を表示する.
	 * @param form 登録時の入力チェック用UserForm
	 * @param model 値を受け渡す
	 * @return ユーザ登録画面（管理者用）
	 */
	@GetMapping("/user/update")
	public String getUserUpdate(@ModelAttribute UserForm form, Model model) {
		return "user/newuser";
	}
	/**
	 * 1件分のユーザ情報でデータベースを更新する.
	 * パスワードの更新が不要の場合は、画面側で何も値を設定しないものとする.
	 * @param form 更新するユーザ情報(パスワードは平文)
	 * @param bindingResult データバインド実施結果
	 * @param principal ログイン情報
	 * @param model 値を受け渡す
	 * @return ユーザ一覧画面
	 */
	@PostMapping(value = "/user/detail", params = "update")
	public String postUserDetailUpdate(@ModelAttribute @Validated UserFormForUpdate form,
			BindingResult bindingResult,
			Principal principal,
			Model model) {

		// 入力チェックに引っかかった場合、前の画面に戻る
		if (bindingResult.hasErrors()) {
			return getUserDetail(form, form.getUser_id(), principal, model);
		}

		log.info("[" + principal.getName() + "]ユーザ更新:" + form.toString());

		// ダークモードは更新しない為、設定無し
		UserData data = new UserData();
		data.setUser_id(form.getUser_id());
		data.setUser_name(form.getUser_name());
		data.setUser_authority(form.getUser_authority());
		data.setUser_status(form.isUser_status());


		boolean result = false;

		if (form.getEncrypted_password() == null || form.getEncrypted_password().equals("")) {
			// パスワード更新有
			result = userService.updateOne(data);
		} else {
			// パスワード更新無
			data.setEncrypted_password(form.getEncrypted_password());
			result = userService.updateOneWithPassword(data);
		}

		if (result) {
			log.info("[" + principal.getName() + "]ユーザ更新成功");
			model.addAttribute("result", "ユーザ更新成功");
		} else {
			log.warn("[" + principal.getName() + "]ユーザ更新失敗");
			model.addAttribute("result", "ユーザ更新失敗");
		}

		return getUserList(model);
	}

	/**
	 * 1件分のユーザ情報をデータベースから削除する.
	 * @param user_id 削除するユーザID
	 * @param principal ログイン情報
	 * @param model 値を受け渡す
	 * @return ユーザ一覧画面
	 */
	@PostMapping("/user/delete")
	public String getUserDelete(@RequestParam("user_id")String user_id, Principal principal, Model model) {
		boolean result = userService.deleteOne(user_id);
		if(result) {
			return getUserList(model);
		}else {
			String errormsg = "ユーザを削除できませんでした";
			model.addAttribute("errormsg",errormsg);
			return getUserList(model);
		}
	}

	/**
	 * ユーザ情報を検索する
	 * @param category カテゴリー
	 * @param keyword キーワード
	 * @param principal ログインユーザ
	 * @param model モデル
	 * @return ユーザ一覧画面
	 */
	@PostMapping("/user/search")
	public String searchReport(@RequestParam("category") String category, @RequestParam("keyword") String keyword, Principal principal, Model model ) {
		System.out.println("やあ");
		//エンティティクラスを作成
		UserEntity userEntity = new UserEntity();
		userEntity = userService.selectSearch(category,keyword);
		System.out.println(userEntity);
		model.addAttribute("userEntity" , userEntity);

		return "/user/userList";
	}

	/**
	 * ユーザ状態を反転する
	 * @param user_id ユーザID
	 * @param principal ログインユーザ
	 * @param model モデル
	 * @return ユーザ一覧画面
	 */
	@GetMapping("/user/reverse/{id}")
	public String getReverseStatus(@PathVariable("id") String user_id,
			Principal principal, Model model) {

		boolean status_flg = userService.getStatus(user_id);
		userService.reverseStatus(status_flg,user_id);
		return getUserList(model);
	}

}
