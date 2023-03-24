package com.group3.camping_project.Services.MessageService;

import com.group3.camping_project.Services.ChatroomService.IChatroomService;
import com.group3.camping_project.entities.Chatroom;
import com.group3.camping_project.entities.Message;
import com.group3.camping_project.entities.User;
import com.group3.camping_project.repository.IChatroomRepo;
import com.group3.camping_project.repository.IMessageRepo;
import com.group3.camping_project.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    IMessageRepo iMessageRepo;
    @Autowired
    IChatroomRepo iChatroomRepo;
    @Autowired
    IUserRepo iUserRepo;
    @Override
    public Message addMessage(Message message,int senderId,int chatroomId) {
        User sender=iUserRepo.findById(senderId).get();
        Chatroom chatroom=iChatroomRepo.findById(chatroomId).get();
        message.setSender(sender);
        message.setChatroom(chatroom);
       return  iMessageRepo.save(message);
    }

    @Override
    public void  deleteMessage(int idMessage) {
         iMessageRepo.deleteById(idMessage);
    }

    @Override
    public List<Message> getAllByChatroomId(int chatroomId) {
        return iMessageRepo.findByChatroom_Id(chatroomId);
    }
}