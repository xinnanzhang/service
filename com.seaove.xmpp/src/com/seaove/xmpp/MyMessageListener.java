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
	 * ������Ϣ  �ش���Ϣ
	 */
	
	@Override
	public void processPacket(Packet arg0) {
		//����ͻ��˷��͹�������Ϣ
		Message clientMsg=(Message) arg0;
		String packetId=clientMsg.getPacketID();
		String clientStr=clientMsg.getBody();
		String to = clientMsg.getFrom();
		
		//��Ҫ�ش�����Ϣ
		Message sendMsg=new Message();
		sendMsg.setFrom(form);
		sendMsg.setPacketID(packetId);
		sendMsg.setTo(to);
		sendMsg.setType(Message.Type.CHAT);
		
		if(clientStr == null) return;
		//��������Ӧ�ļ�  ֵ
		String clientKey = clientStr.substring(0,clientStr.indexOf(":")).trim();
		String clientValue = clientStr.substring(clientStr.indexOf(":")+1);
		//������־
		FileTool.setlogfile("File/logfile",  FileTool.ToDateString()+":"+clientStr);
		if(!clientStr.contains(":")){//����Ĺ���
			sendMsg.setBody("error:format");
		}else{
			String value=FileTool.getReturnStr(FileTool.getRoot()+"src/key_value.properties", clientKey);
			try {
				XmppService xmppClass = (XmppService) Class.forName(value).newInstance();
				String body=xmppClass.update(clientValue);//�Դ������Ĳ������д���
				sendMsg.setBody(body);
				connection.sendPacket(sendMsg);//������ɺ�ش���Ϣ
			} catch (Exception e) {
				e.printStackTrace();
				connection.sendPacket(sendMsg);
			} finally{
				connection.close();
			}
		}
	}
}
