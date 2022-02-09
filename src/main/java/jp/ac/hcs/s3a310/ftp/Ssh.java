package jp.ac.hcs.s3a310.ftp;

	import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class Ssh {

    public void sh(String commandAgument) throws JSchException, SftpException, FileNotFoundException, IOException {

        //サーバ
        String host = "10.11.39.165";
        //ポート
        int port = 22;
        //ユーザ
        String user = "oracle";
        //パスワード
        String password = "oracle";
        //コマンド

        String command = "sh /home/oracle/uploadMovie/a.sh ";
        JSch jsch;
        Session session = null;
        ChannelExec channel = null;
        BufferedInputStream bin = null;
        command= command +" "+commandAgument;
        try {
            //接続
            jsch = new JSch();
            session = jsch.getSession(user, host, port);
            //known_hostsのチェックをスキップ
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(password);

            session.connect();

            channel = (ChannelExec) session.openChannel("exec");

            channel.setCommand(command);

            channel.connect();


            //コマンド実行
            bin = new BufferedInputStream(channel.getInputStream());
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int length;
            while (true) {
                length = bin.read(buf);
                if (length == -1) {
                    break;
                }
                bout.write(buf, 0, length);
            }
            //標準出力
            System.out.format("実行結果=%1$s", new String(bout.toByteArray(), StandardCharsets.UTF_8));
        }
        finally {
            if (bin != null) {
                try {
                    bin.close();
                }
                catch (IOException e) {
                }
            }
            if (channel != null) {
                try {
                    channel.disconnect();
                }
                catch (Exception e) {
                }
            }
            if (session != null) {
                try {
                    session.disconnect();
                }
                catch (Exception e) {
                }
            }
        }
    }
}