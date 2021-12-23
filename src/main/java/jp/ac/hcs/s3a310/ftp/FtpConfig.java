package jp.ac.hcs.s3a310.ftp;

import java.io.Serializable;


public class FtpConfig implements Serializable {

	  private static final long serialVersionUID = 1L;
	  public String hostName;
	  public int port;
	  public String userName;
	  public String password;
	  public boolean binaryTransfer;
	  public boolean usePassiveMode;
	  public String hostPath;
	  public String localPath;
	  public String encoding;
	}
