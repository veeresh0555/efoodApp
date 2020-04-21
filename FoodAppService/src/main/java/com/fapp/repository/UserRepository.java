package com.fapp.repository;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fapp.model.UserDetails;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long> {

	
	 List<UserDetails> findByUsername(long userid, Pageable pageable);
}
