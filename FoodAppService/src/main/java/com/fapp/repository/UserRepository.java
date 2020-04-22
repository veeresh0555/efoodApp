package com.fapp.repository;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fapp.model.UserDetails;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long> {

	@Query("select u from UserDetails u where u.id=:id")
	 List<UserDetails> findByUsername(@Param("id") long id, Pageable pageable);
}
