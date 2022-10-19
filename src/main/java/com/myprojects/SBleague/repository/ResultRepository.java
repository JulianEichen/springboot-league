package com.myprojects.SBleague.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myprojects.SBleague.model.Result;

public interface ResultRepository extends JpaRepository<Result, String>{
	Result findById(Long id);
}
