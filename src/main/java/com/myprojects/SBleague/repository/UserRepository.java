package com.myprojects.SBleague.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myprojects.SBleague.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
