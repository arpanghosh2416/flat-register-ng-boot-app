package com.flat.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flat.app.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
