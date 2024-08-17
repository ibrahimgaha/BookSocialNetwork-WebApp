package com.gaha.book.entities.book;

import org.springframework.stereotype.Service;

import com.gaha.book.entities.book.file.FileUtils;

@Service
public class BookMapper {
	public Book toBook(BookRequest request) {
		return Book.builder().id(request.id()).title(request.title()).isbn(request.isbn())
				.authorName(request.authorName()).synopsis(request.synopsis()).archived(false)
				.shareable(request.shareable()).build();
	}

	public BookResponse toBookResponse(Book book) {
		return BookResponse.builder().id(book.getId()).title(book.getTitle()).authorName(book.getAuthorName())
				.isbn(book.getIsbn()).synopsis(book.getSynopsis()).rate(book.getRate()).archived(book.isArchived())
				.shareable(book.isShareable()).owner(book.getOwner().fullName())
				.cover(FileUtils.readFileFromLocation(book.getBookCover())).build();
	}

	public BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory history) {
		return BorrowedBookResponse.builder().id(history.getBook().getId()).title(history.getBook().getTitle())
				.authorName(history.getBook().getAuthorName()).isbn(history.getBook().getIsbn())
				.rate(history.getBook().getRate()).returned(history.isReturned())
				.returnedApproved(history.isReturnApproved()).build();
	}
}