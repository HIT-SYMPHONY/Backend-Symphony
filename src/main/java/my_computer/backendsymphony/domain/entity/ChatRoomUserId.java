package my_computer.backendsymphony.domain.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class ChatRoomUserId implements Serializable {

    private String chatRoom;
    private String user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatRoomUserId)) return false;
        ChatRoomUserId that = (ChatRoomUserId) o;
        return Objects.equals(chatRoom, that.chatRoom) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatRoom, user);
    }
}
