package com.gaha.book.entities.FeedBack;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gaha.book.common.PageResponse;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
@Tag(name = "FeedBack")
public class FeedBackController {

	private final FeedBackService feedBackService;

	@PostMapping("/path")
	public ResponseEntity<Integer> saveFeedBack(@Valid @RequestBody FeedBackRequest request,
			Authentication connectedUser) {
		return ResponseEntity.ok(feedBackService.save(request, connectedUser));
	}

	@GetMapping("/book/{bookId}")
	public ResponseEntity<PageResponse<FeedBackResponse>> findAllFeedBacksByBook(@PathVariable("bookId") Integer bookId,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size, Authentication connectedUser) {

		return ResponseEntity.ok(feedBackService.findAllFeedBacksByBook(bookId, page, size, connectedUser));
	}
}
