package com.harish.flexfit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harish.flexfit.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
