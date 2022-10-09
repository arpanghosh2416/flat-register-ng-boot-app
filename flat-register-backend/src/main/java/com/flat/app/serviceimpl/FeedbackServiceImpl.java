package com.flat.app.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flat.app.entity.Feedback;
import com.flat.app.repository.FeedbackRepository;
import com.flat.app.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Override
	public List<Feedback> getAllFeedbacks() {
		return feedbackRepository.findAll();
	}

	@Override
	public Feedback sendFeedback(Feedback feedback) {
		return feedbackRepository.save(feedback);
	}

}
