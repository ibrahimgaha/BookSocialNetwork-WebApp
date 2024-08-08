package com.gaha.book.entities.FeedBack;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.gaha.book.common.PageResponse;
import com.gaha.book.entities.User;
import com.gaha.book.entities.book.Book;
import com.gaha.book.entities.book.BookRepository;
import com.gaha.book.entities.exception.OperationNotPermittedException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FeedBackService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private FeedBackRepository feedBackRepository;

	@Autowired
	private FeedBackMapper feedBackMapper;

	public Integer save(FeedBackRequest request, Authentication connectedUser) {

		Book book = bookRepository.findById(request.bookId())
				.orElseThrow(() -> new EntityNotFoundException("No book found with the id " + request.bookId()));

		if (book.isArchived() || !book.isShareable()) {
			throw new OperationNotPermittedException(
					"You can not give a feed back for an archived or a non shareable book");
		}
		User user = ((User) connectedUser.getPrincipal());
		if (java.util.Objects.equals(book.getOwner().getId(), user.getId())) {
			// throw exception
			throw new OperationNotPermittedException("You cannot give a feedback to your own book");
		}
		FeedBack feedBack = feedBackMapper.toFeedBack(request);
		return feedBackRepository.save(feedBack).getId();
	}

	public PageResponse<FeedBackResponse> findAllFeedBacksByBook(Integer bookId, int page, int size,
			Authentication connectedUser) {
		Pageable pageable = PageRequest.of(page, size);
		User user = ((User) connectedUser.getPrincipal());
		Page<FeedBack> feedBacks = feedBackRepository.findAllByBookId(bookId, pageable);

		List<FeedBackResponse> feedBackResponse = feedBacks.stream()
				.map(f -> feedBackMapper.toFeedBackResponse(f, user.getId())).toList();
		return new PageResponse<>(feedBackResponse, feedBacks.getNumber(), feedBacks.getSize(),
				feedBacks.getTotalElements(), feedBacks.getTotalPages(), feedBacks.isFirst(), feedBacks.isLast());
	}
}
