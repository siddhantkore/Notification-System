package com.jetnotifier.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jetnotifier.notification.domain.entity.User;

import jakarta.persistence.Id;


@Repository
public interface UserRepository extends JpaRepository<User, Id> {
	
}
