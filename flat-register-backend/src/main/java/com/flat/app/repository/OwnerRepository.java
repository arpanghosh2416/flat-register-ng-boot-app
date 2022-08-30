package com.flat.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flat.app.entity.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

	public List<Owner> findByOrderByOwnerNameAsc();

	public List<Owner> findByOrderByOwnerEmailAsc();

	@Query(value = "SELECT * FROM flat_owner NATURAL JOIN flat WHERE flat_id = ?1", nativeQuery = true)
	public Optional<Owner> findByFlatId(Long flatId);

	public List<Owner> findByOwnerName(String ownerName);

	public Optional<Owner> findByOwnerEmail(String ownerEmail);

	public Optional<Owner> findByPhoneNumber(String phoneNumber);

}
