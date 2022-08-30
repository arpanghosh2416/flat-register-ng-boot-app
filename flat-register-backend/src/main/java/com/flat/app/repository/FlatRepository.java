package com.flat.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flat.app.entity.Flat;

@Repository
public interface FlatRepository extends JpaRepository<Flat, Long> {

	public List<Flat> findByStoreyNumber(Long storeyNumber);

	public List<Flat> findByLivingStatus(Boolean livingStatus);

}
