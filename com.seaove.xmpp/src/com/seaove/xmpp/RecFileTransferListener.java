package com.seaove.xmpp;

import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;

/**
 * 文件传输管理器监听
 * @author xinnan
 *
 */
public class RecFileTransferListener implements FileTransferListener {
	public String getFileType(String fileFullName) {
		if (fileFullName.contains(".")) {
			return "." + fileFullName.split("//.")[1];
		} else {
			return fileFullName;
		}
	}

	@Override
	public void fileTransferRequest(FileTransferRequest request) {
		final IncomingFileTransfer inTransfer = request.accept();
		final String fileName = request.getFileName();
		final String fromUser = request.getRequestor();
		final FileTransferRequest request1=request;
		new Thread(){
			public void run(){
				String[] from = fromUser.split("/");
				String instanceId=from[0].substring(0,from[0].indexOf("@"));
			}
		}.start();
	}
}
