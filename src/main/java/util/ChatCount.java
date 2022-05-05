package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

@ServerEndpoint("/chatCount")
@Component
public class ChatCount implements ApplicationContextAware {
	//private static HashMap<Session, String> clients = new HashMap<>();
	private static List<Session> clients = new ArrayList<>();
	static WebChatDao wcd;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		wcd = applicationContext.getBean("webChatDao", WebChatDao.class);
	}

	@OnMessage
	public void onMessage(final String message, final Session session) throws IOException {
		// 웹소켓을 하나 추가. 연결되면 유저 아이디를 여기로 보냄
		// 일반 사용자랑 관리자 쿼리 다르게 써야 함
		// 일반 사용자일 경우, 그룹 아이디가 본인 아이디고, 유저 아이디와 그룹 아이디가 다른 메세지 중 readchk가 n인 메세지 수 체크
		// 관리자일 경우 그룹 아이디와 유저 아이디가 같고 readchk가 n인 메세지 개수 찾아옴
		// 그럼 받아오는 거는 user 아이디만 있으면 되지 않나?
		
		String[] messageArr = message.split(":");
		final String userId = messageArr[0];
		String text = messageArr[1];
		
		if (text.equals("read")) {
			
			if (!userId.equals("admin")) {
				wcd.userReadChkUpdate(userId);
			} else {
				wcd.adminReadChkUpdate();
			}
			
			return;
		}
		
		synchronized (clients) {
			TimerTask task = new TimerTask() {
				@Override
				public void run() {

					int chatCount = 0;

					if (!userId.equals("admin")) {
						chatCount = wcd.userChatCount(userId);
					} else {
						chatCount = wcd.adminChatCount();
					}
					
					for (Session client : clients) {
						try {
							// 해당 사용자가 읽지 않은 메세지가 있으면 개수를 전송
							if (client == session) {
								client.getBasicRemote().sendText("" + chatCount);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}

					}

				}
			};
			
			Timer timer = new Timer(true);
			timer.scheduleAtFixedRate(task, 0, 1000);
		}
	}

	@OnOpen
	public void onOpen(Session session) {
		clients.add(session);
		System.out.println("open chatCount: " + session);
	}

	@OnClose
	public void onClose(Session session) {
		clients.remove(session);
		System.out.println("close chatCount: " + session);
	}

}
