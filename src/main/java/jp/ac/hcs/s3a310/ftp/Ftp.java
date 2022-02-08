package jp.ac.hcs.s3a310.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;



public class Ftp {

	private FtpConfig config;
	private FTPClient client;
	private boolean isConnected;



	/**
	 * コンストラクタ
	 *
	 * @param config FtpConfig
	 */
	public Ftp(FtpConfig config) {
		this.config = config;
		this.isConnected = false;
	}

	/**
	 * コンストラクタ
	 *
	 * @param hostName ホスト名(IPアドレス:10.11.39.165)
	 * @param port ポート番号(21)
	 * @param userName ユーザー
	 * @param password パスワード
	 * @param binaryTransfer バイナリ転送モード(true: Yes, false: No)
	 * @param usePassiveMode パッシブモード(true: Yes, false: No)
	 * @param hostPath ホスト側パス
	 * @param Path ローカル側パス
	 * @param encoding エンコーディング(SJIS, MS932, EUC_JP など)
	 * @param fileName 該当ディレクトリ名
	 */
	public Ftp(    String localPath ,String fileName ) {
		this.config = new FtpConfig();

		this.config.hostName = "10.11.39.165";
		this.config.port = 21;
		this.config.userName = "oracle";
		this.config.password = "oracle";
		this.config.binaryTransfer = true;
		this.config.usePassiveMode = true;

		this.config.hostPath = "/home/oracle/uploadMovie";
		this.config.localPath = localPath;
		this.config.encoding = "SJIS";
		this.isConnected = false;
		this.config.fileName=fileName;
	}

	/**
	 * パラメータチェック
	 *
	 * @return true: 正常, false: 異常
	 */
	private boolean check() {
		boolean success = true;
		// ホスト名
		if ( isEmpty(this.config.hostName) ) {
			System.out.println("hostName Parameter Failed");
			success = false;
		}
		// ポート
		if ( this.config.port == 0 ) {
			System.out.println("port Parameter Failed");
			success = false;
		}
		// ユーザー名
		if ( isEmpty(this.config.userName) ) {
			System.out.println("userName Parameter Failed");
			success = false;
		}
		// パスワード
		if ( isEmpty(this.config.password) ) {
			System.out.println("password Parameter Failed");
			success = false;
		}
		// ホストパス
		if ( isEmpty(this.config.hostPath) ) {
			System.out.println("hostPath Parameter Failed");
			success = false;
		}
		// ローカルパス
		if ( isEmpty(this.config.localPath) ) {
			System.out.println("localPath Parameter Failed");
			success = false;
		}
		if ( isEmpty(this.config.encoding) ) {
			System.out.println("encoding Parameter Failed");
			success = false;
		}

		// 該当ファイル
		if ( isEmpty(this.config.fileName) ) {
			System.out.println("fileName Parameter Failed");
			success = false;
		}

		return success;
	}

	/**
	 * 接続
	 *
	 * @return true: 正常, false: 異常
	 * @throws Exception
	 */
	public boolean connect() throws Exception {
		boolean success = check();
		if ( !success )
			return false;
		this.client = new FTPClient();
		System.out.println("connect....");
		this.client.setControlEncoding(this.config.encoding);
		client.connect(this.config.hostName, this.config.port);
		System.out.println("Connected to Server: " + this.config.hostName + " on " + this.client.getRemotePort());
		System.out.println(this.client.getReplyString());
		this.client.login(this.config.userName, this.config.password);
		System.out.println(client.getReplyString());
		if ( !FTPReply.isPositiveCompletion(this.client.getReplyCode()) ) {
			System.out.println("Login Failed");
			this.client.disconnect();
			return false;
		} else {
			this.isConnected = true;
		}
		// Binary転送モードの場合
		if ( this.config.binaryTransfer ) {
			this.client.setFileType(FTP.BINARY_FILE_TYPE);
			System.out.println("Mode binaryTransfer: true");
		}
		// PASVモードの場合
		if ( this.config.usePassiveMode ) {
			this.client.enterLocalPassiveMode();
			System.out.println("Mode usePassiveMode: ON");
		} else {
			this.client.enterLocalActiveMode();
			System.out.println("Mode usePassiveMode: OFF");
		}
		// ディレクトリ移動
		success = this.client.changeWorkingDirectory(this.config.hostPath);
		if ( !success ) {
			System.out.println("Server Directory Failed");
			this.client.disconnect();
			return false;
		}
		System.out.println(this.client.getReplyString());
		success = FTPReply.isPositiveCompletion(this.client.getReplyCode());

		System.out.println("Connection: " + (success ? "OK" : "NG"));
		System.out.println("-----------------------------------");
		return success;
	}

	/**
	 * 切断
	 *
	 * @return true: 正常, false: 異常
	 * @throws Exception
	 */
	public boolean disconnect() throws Exception {

		if ( this.isConnected ) {
			client.logout();
			System.out.println(client.getReplyString());
			if ( client.isConnected() ) client.disconnect();
		}
		return true;
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public boolean deleteFile(String pathname) throws Exception{
		boolean flg =false;



		//TODO ffmpeg作成後に削除形式を決める。

		// /home/oracle/uploadMovie +"/"+/pathname
		this.client.deleteFile(this.config.hostPath+"/"+pathname);





		return flg;

	}



	/**
	 * 送信
	 * @param file
	 *
	 * @return true: 正常, false: 異常
	 * @throws Exception
	 */
	public boolean put(File file) throws Exception {
		boolean success = true;

		if ( !this.isConnected )
			success = connect();
		if ( success ) {
			// ディレクトリ移動
			success = this.client.changeWorkingDirectory(this.config.hostPath+"/"+this.config.fileName);

			if(!success) {
				success=this.client.makeDirectory(this.config.hostPath+"/"+this.config.fileName);
				this.client.changeWorkingDirectory(this.config.hostPath+"/"+this.config.fileName);

			}


			if ( !success ) {

				System.out.println("Server Directory Failed 1");
				this.client.disconnect();
				return false;
			}




			return putFiles(file, this.config.hostPath + (this.config.hostPath.endsWith("/") ? "" : "/"));
		}
		return success;
	}

	/**
	 * ファイル送信
	 *
	 * @param file FTPFile
	 * @param hostPath ホスト側パス
	 * @throws Exception
	 */
	private boolean putFiles(File file, String hostPath) throws Exception {
		boolean success = true;
		if ( file.isFile() ) {
			FileInputStream is = null;
			try {
				is = new FileInputStream(file);

				System.out.println("PUT File Name: "  + file.getName());
				this.client.storeFile(hostPath+"/"+this.config.fileName+"/" + file.getName(), is);
				is.close();
				System.out.println("FTP PUT Completed");
			} catch ( Exception e ) {
				System.out.println("FTP PUT Failed: " + file.getName());
				System.out.println(e.getMessage());
				success = false;
			} finally {
				if ( is != null )
					is.close();
			}
		} else if ( file.isDirectory() ) {
			String dirName = hostPath + file.getName() + "/";
			System.out.println("Make Directory: " + dirName);
			// ディレクトリがなければ作る
			success = this.client.makeDirectory(dirName);

			if ( success ) {
				success = this.client.changeWorkingDirectory(dirName);
			} else {
				// ディレクトリが作れない場合、移動してみる
				success = this.client.changeWorkingDirectory(dirName);
				if ( !success ) {
					System.out.println("Server Directory Failed: " + dirName);
					return success;
				}
			}
			System.out.println("-----------------------------------");
			File[] files = file.listFiles();
			for ( File f : files ) {
				success = putFiles(f, dirName);
				if ( !success )
					return success;
			}
		} else {
		}

		return success;
	}

	/**
	 * 受信
	 */
	public boolean get() throws Exception {
		boolean success = true;
		if ( !this.isConnected )
			success = connect();
		if ( success ) {
			// ディレクトリ移動
			success = this.client.changeWorkingDirectory(this.config.hostPath);
			if ( !success ) {
				System.out.println("Server Directory Failed");
				this.client.disconnect();
				return false;
			}
			String fileNames[] = this.client.listNames();
			if ( fileNames != null ) {
				for ( int i = 0; i < fileNames.length; i++ ) {
					System.out.println("Get File Name: "  + fileNames[i]);
				}
			}
			System.out.println("-----------------------------------");
			for (FTPFile f : this.client.listFiles()) {
				getFiles(f, this.config.localPath);
			}
		}
		return success;
	}

	/**
	 * ファイル取得
	 *
	 * @param file FTPFile
	 * @param localPath ローカル側パス
	 * @throws Exception
	 */
	private boolean getFiles(FTPFile file, String localPath) throws Exception {

		boolean success = true;
		if ( !file.getName().equals(".")&&!file.getName().equals("..") ) {
			String currentDir = client.printWorkingDirectory();
			if ( file.isFile() ) {
				String filename = file.getName();
				filename = new String(filename.getBytes("MS932"), "UTF-8");
				String utf8filename = client.printWorkingDirectory() + (currentDir.endsWith("/") ? "" : "/") + file.getName();
				System.out.println("Get File Name: " + filename);
				System.out.println("Get UTF8 File Name: " + utf8filename);
				String localPathName = localPath + (localPath.endsWith("/") ? "" : "/") + filename;
				System.out.println("Local Path Name: " + localPathName);
				FileOutputStream os = null;
				try {
					os = new FileOutputStream(localPathName);
					client.retrieveFile(utf8filename, os);
					os.close();
					System.out.println("FTP GET Completed");
				} catch ( Exception e ) {
					System.out.println("FTP GET Failed: " + filename);
					System.out.println(e.getMessage());
					success = false;
				} finally {
					if ( os != null )
						os.close();
				}
			} else if ( file.isDirectory() ) {
				File localDir = new File(localPath + (localPath.endsWith("/") ? "" : "/") + file.getName());
				String path = localPath;
				if ( !localDir.exists() ) {
					localDir.mkdirs();
					path = localPath + (localPath.endsWith("/") ? "" : "/") + file.getName();
				}
				success = client.doCommand("CWD", client.printWorkingDirectory() + (currentDir.endsWith("/") ? "" : "/") + file.getName());
				for (FTPFile f : client.listFiles()) {
					success = getFiles(f, path);
					if ( !success )
						break;
				}
				//client.doCommand("CDUP", "");
			}
		}
		return success;
	}

	public boolean inputImage(String id,String name) {
		File file = new File("");

		FTPFile[] list;
		try {
			list = this.client.listFiles(config.hostPath);


		} catch (IOException e1) {

			e1.printStackTrace();
		}



		boolean success = false;





		return success;
	}



	/**
	 * 空文字列チェック
	 *
	 * @param value 文字列
	 * @return null または 空文字列 なら true , それ以外なら false
	 */
	public boolean isEmpty(String value) {
		if ( value == null || value.length() == 0 )
			return true;
		else
			return false;
	}

	/**
	 * ホスト側パスセット
	 *
	 * @param hostPath ホスト側パス
	 */
	public void setHostPath(String hostPath) {
		this.config.hostPath = hostPath;
	}

	/**
	 * ローカル側パスセット
	 *
	 * @param localPath ローカル側パス
	 */
	public void setLocalPath(String localPath) {
		this.config.localPath = localPath;
	}
}
