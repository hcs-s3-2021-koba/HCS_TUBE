package jp.ac.hcs.s3a310.storage;

import org.springframework.context.annotation.Configuration;

/**
こいつが原因や
*/
@Configuration("storage")
public class StorageProperties {

	/**
	 * locationにはファイルを格納したい場所を書く！
	 */
	private String location = "upload-dir";

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
