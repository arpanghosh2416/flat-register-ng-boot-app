package com.flat.app.service;

import java.util.List;

import com.flat.app.entity.Feedback;

public interface FeedbackService {

	public List<Feedback> getAllFeedbacks();

	public Feedback sendFeedback(Feedback feedback);

}
