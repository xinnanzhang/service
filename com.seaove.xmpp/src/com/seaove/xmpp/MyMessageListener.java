package com.seaove.xmpp;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import com.seaove.xmpp.service.XmppService;
import com.seaove.xmpp.util.FileTool;

public class MyMessageListener implements PacketListener {
	public static String form=XMPPActivator.userName+"@xmpp.siteview.com/MiddServer";
	XMPPConnection connection;
	
	public MyMessageListener(XMPPConnection connection){
		this.connection = connection;
	}
	
	/**
	 * 接收消息  回传消息
	 */
	
	@Override
	public void processPacket(Packet arg0) {
		//处理客户端发送过来的消息
		Message clientMsg=(Message) arg0;
		String packetId=clientMsg.getPacketID();
		String clientStr=clientMsg.getBody();
		String to = clientMsg.getFrom();
		
		//需要回传的消息
		Message sendMsg=new Message();
		sendMsg.setFrom(form);
		sendMsg.setPacketID(packetId);
		sendMsg.setTo(to);
		sendMsg.setType(Message.Type.CHAT);
		
		if(clientStr == null) return;
		//传过来对应的键  值
		String clientKey = clientStr.substring(0,clientStr.indexOf(":")).trim();
		String clientValue = clientStr.substring(clientStr.indexOf(":")+1);
		//保存日志
		FileTool.setlogfile("File/logfile",  FileTool.ToDateString()+":"+clientStr);
		if(!clientStr.contains(":")){//定义的规则
			sendMsg.setBody("error:format");
		}else{
			String value=FileTool.getReturnStr(FileTool.getRoot()+"src/key_value.properties", clientKey);
			try {
				XmppService xmppClass = (XmppService) Class.forName(value).newInstance();
				String body=xmppClass.update(clientValue);//对传过来的参数进行处理
				sendMsg.setBody(body);
				connection.sendPacket(sendMsg);//处理完成后回传消息
			} catch (Exception e) {
				e.printStackTrace();
				connection.sendPacket(sendMsg);
			} finally{
				connection.close();
			}
		}
	}
}
