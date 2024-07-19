package com.gaha.book.entities.book;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class BookMapper {

	public Book toBook(@Valid BookRequest request) {

		return Book.builder().id(request.id()).title(request.title()).authorName(request.authorName())
				.synopsis(request.synopsis()).archived(false).shareable(request.shareable()).build();
	}

	public BookResponse toBookResponse(Book book) {
		return BookResponse.builder().id(book.getId()).title(book.getTitle()).authorName(book.getAuthorName())
				.synopsis(book.getSynopsis()).rate(book.getRate()).archived(book.isArchived())
				.shareable(book.isShareable()).owner(book.getOwner().fullName())
				// .cover(null)

				.build();
	}

	public BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory history) {
		return BorrowedBookResponse.builder().id(history.getBook().getId()).title(history.getBook().getTitle())
				.authorName(history.getBook().getAuthorName()).rate(history.getBook().getRate())
				.returned(history.isReturned()).returnedApproved(history.isReturnApproved()).build();
	}

}
