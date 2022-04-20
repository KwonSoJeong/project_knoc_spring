package util;

import java.io.IOException;
import java.util.HashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import model.WebChat;
import service.WebChatDao;

@ServerEndpoint("/groupchat")
public class GroupChat {
	private static HashMap<Session, String> clients = new HashMap<>();
	
	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		WebChat webChat = new WebChat(message.split(":"));
		
		synchronized (clients) {
			clients.put(session, webChat.getGroupId());
			
			if (webChat.getGroupId() != "") {
				// 로그인 정보가 없을 때는 db에 메세지 데이터를 추가시키지 않음
				WebChatDao wcd = new WebChatDao();
				int no = wcd.nextSeq();
				
				webChat.setNo(no);
				wcd.insertChat(webChat);
			}
			
			for (Session client : clients.keySet()) {
				if (!client.equals(session) && clients.get(client).equals(webChat.getGroupId())) {
					client.getBasicRemote().sendText(webChat.toString());
				}
			}
		}
	}
	
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("open: " + session);
		// clients.put(session, null);
	}
	
	@OnClose
	public void onClose(Session session) {
		clients.remove(session);
		System.out.println("close: " + session);
	}
}
