//import java.time.LocalDateTime;
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.example.app.domain.Chats;
//import com.example.app.mapper.ChatMapper;
//
//@Service
//public class ChatService {
//
//    private final ChatMapper chatMapper;
//    private final UsersService usersService;
//
//    public ChatService(ChatMapper chatMapper, UsersService usersService) {
//        this.chatMapper = chatMapper;
//        this.usersService = usersService;
//    }
//
//    // すべてのチャットメッセージを取得
//    public List<Chats> getAllChats() {
//        return chatMapper.selectAllChats();
//    }
//
//    // チャットメッセージを送信
//    @Transactional
//    public void sendChat(Chats chat) {
//        // チャットメッセージの作成日時と更新日時を設定
//        chat.setCreatedAt(LocalDateTime.now());
//        chat.setUpdatedAt(LocalDateTime.now());
//
//        // メッセージの保存
//        chatMapper.insertChat(chat);
//    }
//
//    // チャットメッセージを更新
//    @Transactional
//    public void updateChat(Chats chat) {
//        chat.setUpdatedAt(LocalDateTime.now());
//        chatMapper.updateChat(chat);
//    }
//
//    // チャットメッセージを削除
//    @Transactional
//    public void deleteChat(Integer id) {
//        chatMapper.deleteChat(id);
//    }
//}
