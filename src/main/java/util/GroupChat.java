package util;

import java.io.IOException;
import java.util.HashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import model.WebChat;
import service.WebChatDao;

@ServerEndpoint("/groupchat")
@Component
public class GroupChat implements ApplicationContextAware {
	private static HashMap<Session, String> clients = new HashMap<>();
	static WebChatDao wcd;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		wcd = applicationContext.getBean("webChatDao", WebChatDao.class);
	}
	
	
	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		WebChat webChat = new WebChat(message.split(":"));
		// 처음 연결 되었을 때는 groupId를 map에 저장만 하고 메세지는 보내지 않음
		// 단순히 단어로 확인하는 코드라 추후에 수정 필요
		if (webChat.getMessage().equals("connected")) {
			clients.put(session, webChat.getGroupId());
			return;
		}
		
		synchronized (clients) {
			//clients.put(session, webChat.getGroupId());
			
			if (webChat.getGroupId() != "") {
				// 로그인 정보가 없을 때는 db에 메세지 데이터를 추가시키지 않음
				int no = wcd.nextSeq();
				webChat.setNo(no);
				webChat.setReadChk("N");
				wcd.insertChat(webChat);
			}
			
			for (Session client : clients.keySet()) {
				if (!client.equals(session) && clients.get(client).equals(webChat.getGroupId())) {
					client.getBasicRemote().sendText(webChat.toString());
					
					// 상대방이 연결상태일 경우 readchk 업데이트
					if (!webChat.getUserId().equals("admin")) {
						wcd.adminReadChkUpdate();
					} else {
						wcd.userReadChkUpdate(webChat.getGroupId());
					}
					
					
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
