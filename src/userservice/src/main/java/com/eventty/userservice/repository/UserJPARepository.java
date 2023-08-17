package com.eventty.userservice.repository;

import com.eventty.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJPARepository extends JpaRepository<User, Long>{
}
