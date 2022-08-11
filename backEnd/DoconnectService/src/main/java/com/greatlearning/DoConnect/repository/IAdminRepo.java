package com.greatlearning.DoConnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.greatlearning.DoConnect.entity.Admin;

@Repository
public interface IAdminRepo extends JpaRepository<Admin, Long> {

	public Admin findByEmail(String email);

}
