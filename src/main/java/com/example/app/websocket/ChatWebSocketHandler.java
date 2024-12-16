//package  com.example.app.websocket;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//
//import com.example.app.domain.Chats;
//import com.example.app.domain.Users;
//import com.example.app.service.ChatService;
//import com.example.app.service.UsersService;
//
//@Service
//public class ChatWebSocketHandler {
//
//    private final SimpMessagingTemplate messagingTemplate;
//    private final ChatService chatService;
//    private final UsersService usersService;
//
//    public ChatWebSocketHandler(SimpMessagingTemplate messagingTemplate, ChatService chatService, UsersService usersService) {
//        this.messagingTemplate = messagingTemplate;
//        this.chatService = chatService;
//        this.usersService = usersService;
//    }
//
//    // メッセージ送信処理
//    public void sendMessage(String message, Integer senderId, List<Integer> recipientIds) {
//        // 送信者の情報を取得
//        Users sender = usersService.findById(senderId);
//        if (sender == null) {
//            System.out.println("送信者が見つかりません");
//            return;
//        }
//
//        // 受信者の情報を取得
//        List<Users> recipients = usersService.findById(recipientIds);
//        if (recipients.isEmpty()) {
//            System.out.println("受信者が見つかりません");
//            return;
//        }
//
//        // メッセージをデータベースに保存
//        Chats chat = new Chats();
//        chat.setUser(sender); // 送信者を設定
//        chat.setRecipients(recipients); // 受信者を設定
//        chat.setContent(message); // メッセージ内容
//        chat.setCreatedAt(LocalDateTime.now());
//        chat.setUpdatedAt(LocalDateTime.now());
//
//        try {
//            chatService.sendChat(chat);  // メッセージをデータベースに保存
//        } catch (Exception e) {
//            // エラーハンドリング
//            return;
//        }
//
//        // メッセージをWebSocketで送信（送信者と受信者に対して）
//        messagingTemplate.convertAndSend("/topic/messages", chat);
//    }
//}
