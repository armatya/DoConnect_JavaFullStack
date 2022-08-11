package com.greatlearning.doconnectchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greatlearning.doconnectchat.entity.Message;

@Repository
public interface IMessageRepo extends JpaRepository<Message, Long> {

}
