package jp.ac.hcs.s3a310.user;

/**
 * ユーザ状態を定義する.
 * 1 一般
 * 2 管理者
 */
public enum UserStatus {
	GENERAL(1), ADMIN(2);

	private final int code;

	private UserStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
