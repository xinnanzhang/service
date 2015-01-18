package com.seaove.xmpp;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.SSLXMPPConnection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;


public class XMPPActivator extends Thread{
	static String id = "xmpp.siteview.com";
	static int port = 5223;
	public static String userName = "itsm";
	static String userPwd = "123456";
	public static XMPPConnection connection;
	/**需查看文档*/
	public static PacketFilter messagefilter;
	/**需查看文档*/
	public static PacketListener messageListener;
	/**需查看文档*/
	public RecFileTransferListener fileTransferListener;
	
	public void run() {
		final XMPPActivator xmppa=new XMPPActivator();
			xmppa.init();
//		XMPPActivator.setStatus(true, "Activati",connection);
	}
	
	public void init(){
		System.out.println("----------------------------------");
		XMPPConnection con = null;
		try {
			con = new SSLXMPPConnection(id, port);
			con.login(userName, userPwd);
		} catch (XMPPException e1) {
			e1.printStackTrace();
		}
		connection = con;
		if(connection==null || !connection.isConnected()){
			try {
				this.wait(10000);
				init();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		//文件监听 传输
		fileTransferListener=new RecFileTransferListener();
		FileTransferManager transferManager = new FileTransferManager(connection);
		transferManager.addFileTransferListener(fileTransferListener);
		//包的  监听 过滤
		messageListener = new MyMessageListener(connection);
		messagefilter=new PacketTypeFilter(Message.class);
		connection.addPacketListener(messageListener, messagefilter);
		//conect listener
		connection.addConnectionListener(new ConnectionListener() {
			public void connectionClosedOnError(Exception arg0) {
				final XMPPActivator xmppa=new XMPPActivator();
				xmppa.init();
			}
			
			public void connectionClosed() {
				final XMPPActivator xmppa=new XMPPActivator();
				xmppa.init();
			}
		});
	}
	
	/*
	 * 设置自己的状态
	 */
	public static void setStatus(boolean available, String status,
			XMPPConnection connection) {
		Presence.Type type = available ? Type.AVAILABLE : Type.UNAVAILABLE;
		Presence presence = new Presence(type);
		presence.setStatus(status);
		connection.sendPacket(presence);
	}
}
