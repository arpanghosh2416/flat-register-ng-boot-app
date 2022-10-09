package com.flat.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flat.app.entity.Feedback;
import com.flat.app.service.FeedbackService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/feedback")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	@GetMapping("/get-all-feedbacks")
	public ResponseEntity<?> getAllFeedbacks() {
		List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
		return ResponseEntity.ok(feedbacks);
	}

	@PostMapping("/send-feedback")
	public ResponseEntity<?> sendFeedback(@RequestBody @Valid Feedback feedback) {
		Feedback _feedback = feedbackService.sendFeedback(feedback);
		return ResponseEntity.status(201).body(_feedback);
	}

}
