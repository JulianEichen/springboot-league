package com.myprojects.SBleague.usermanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myprojects.SBleague.usermanagement.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

}
