package com.gaha.book.entities.book;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gaha.book.common.PageResponse;
import com.gaha.book.entities.User;
import com.gaha.book.entities.book.file.FileStorageService;
import com.gaha.book.entities.exception.OperationNotPermittedException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class BookService {

	@Autowired
	private BookMapper bookMapper;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BookTransactionHistoryRepository bookTransactionHistoryRepository;

	@Autowired
	private FileStorageService fileStorageService;

	// save a book
	public Integer save(@Valid BookRequest request, Authentication connectedUser) {
		User user = ((User) connectedUser.getPrincipal());
		Book book = bookMapper.toBook(request);
		book.setOwner(user);
		return bookRepository.save(book).getId();
	}

	// find a book by it's own id
	public BookResponse findById(Integer bookId) {
		// TODO Auto-generated method stub
		return bookRepository.findById(bookId).map(bookMapper::toBookResponse)
				.orElseThrow(() -> new EntityNotFoundException("NO book found with  the ID :: 0" + bookId));
	}

	// findAllBooks and make a page
	public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser) {

		User user = ((User) connectedUser.getPrincipal());
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
		Page<Book> books = bookRepository.findAllDisplayableBooks(pageable, user.getId());

		List<BookResponse> bookResponse = books.stream().map(bookMapper::toBookResponse).collect(Collectors.toList());
		return new PageResponse<>(bookResponse, books.getNumber(), books.getSize(), books.getTotalElements(),
				books.getTotalPages(), books.isFirst(), books.isLast());
	}

	// findAllBooks BY their owners
	public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser) {
		User user = ((User) connectedUser.getPrincipal());
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
		Page<Book> books = bookRepository.findAll(BookSpecification.withOwnerId(user.getId()), pageable);

		List<BookResponse> bookResponse = books.stream().map(bookMapper::toBookResponse).collect(Collectors.toList());
		return new PageResponse<>(bookResponse, books.getNumber(), books.getSize(), books.getTotalElements(),
				books.getTotalPages(), books.isFirst(), books.isLast());
	}
	// findAllBooks borrowed

	public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser) {
		User user = ((User) connectedUser.getPrincipal());
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

		Page<BookTransactionHistory> allBorrowedBooks = bookTransactionHistoryRepository.findAllBorrowedBooks(pageable,
				user.getId());
		List<BorrowedBookResponse> bookResponse = allBorrowedBooks.stream().map(bookMapper::toBorrowedBookResponse)
				.toList();
		return new PageResponse<>(bookResponse, allBorrowedBooks.getNumber(), allBorrowedBooks.getSize(),
				allBorrowedBooks.getTotalElements(), allBorrowedBooks.getTotalPages(), allBorrowedBooks.isFirst(),
				allBorrowedBooks.isLast());
	}
	// findAllBooks returned

	public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser) {
		User user = ((User) connectedUser.getPrincipal());
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

		Page<BookTransactionHistory> allReturnedBooks = bookTransactionHistoryRepository.findAllReturnedBooks(pageable,
				user.getId());
		List<BorrowedBookResponse> bookResponse = allReturnedBooks.stream().map(bookMapper::toBorrowedBookResponse)
				.toList();
		return new PageResponse<>(bookResponse, allReturnedBooks.getNumber(), allReturnedBooks.getSize(),
				allReturnedBooks.getTotalElements(), allReturnedBooks.getTotalPages(), allReturnedBooks.isFirst(),
				allReturnedBooks.isLast());
	}

	// update book

	public Integer updateShareableStatus(Integer bookId, Authentication connectedUser) {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new EntityNotFoundException("No book found with the id " + bookId));
		User user = ((User) connectedUser.getPrincipal());

		if (!java.util.Objects.equals(book.getOwner().getId(), user.getId())) {
			// throw exception
			throw new OperationNotPermittedException("You cannot update others Books shareable Status");
		}

		book.setShareable(!book.isShareable());
		bookRepository.save(book);

		return bookId;
	}

	// updateArchivedStatus
	public Integer updateArchivedStatus(Integer bookId, Authentication connectedUser) {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new EntityNotFoundException("No book found with the id " + bookId));
		User user = ((User) connectedUser.getPrincipal());

		if (!java.util.Objects.equals(book.getOwner().getId(), user.getId())) {
			// throw exception
			throw new OperationNotPermittedException("You cannot update others Books Archived Status");
		}

		book.setArchived(!book.isArchived());
		bookRepository.save(book);

		return bookId;
	}

	// borrowBook

	public Integer borrowBook(Integer bookId, Authentication connectedUser) {
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book Not Found ! "));
		if (book.isArchived() || !book.isShareable()) {
			throw new OperationNotPermittedException("The requested book can not be borrowed ! ");
		}
		User user = ((User) connectedUser.getPrincipal());

		if (java.util.Objects.equals(book.getOwner().getId(), user.getId())) {
			// throw exception
			throw new OperationNotPermittedException("You cannot borrow your own book");
		}

		final boolean isAltreadyBorrowed = bookTransactionHistoryRepository.isAlreadyBorrowedByUser(bookId,
				user.getId());

		if (isAltreadyBorrowed) {
			throw new OperationNotPermittedException("the requested book is already borrowed ");

		}
		BookTransactionHistory bookTransactionHistory = BookTransactionHistory.builder().user(user).book(book)
				.returnApproved(false).build();
		return bookTransactionHistoryRepository.save(bookTransactionHistory).getId();
	}

	// returnBorrowedBook

	public Integer returnBorrowedBook(Integer bookId, Authentication connectedUser) {

		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new EntityNotFoundException("No book found with the id " + bookId));

		if (book.isArchived() || !book.isShareable()) {
			throw new OperationNotPermittedException("The requested book can not be borrowed ! ");
		}
		User user = ((User) connectedUser.getPrincipal());
		if (java.util.Objects.equals(book.getOwner().getId(), user.getId())) {
			// throw exception
			throw new OperationNotPermittedException("You cannot return or borrow your own book");
		}
		BookTransactionHistory bookTransactionHistory = bookTransactionHistoryRepository
				.findByBookIdAndUserId(bookId, user.getId())
				.orElseThrow(() -> new OperationNotPermittedException("you did not borrow this book "));
		bookTransactionHistory.setReturned(true);
		return bookTransactionHistoryRepository.save(bookTransactionHistory).getId();
	}

	// approveReturnBorrowedBook
	public Integer approveReturnBorrowedBook(Integer bookId, Authentication connectedUser) {

		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new EntityNotFoundException("No book found with the id " + bookId));

		if (book.isArchived() || !book.isShareable()) {
			throw new OperationNotPermittedException("The requested book can not be borrowed ! ");
		}
		User user = ((User) connectedUser.getPrincipal());
		if (java.util.Objects.equals(book.getOwner().getId(), user.getId())) {
			// throw exception
			throw new OperationNotPermittedException("You cannot return or borrow your own book");
		}

		BookTransactionHistory bookTransactionHistory = bookTransactionHistoryRepository
				.findByBookIdAndOwnerId(bookId, user.getId())
				.orElseThrow(() -> new OperationNotPermittedException("The book is not reuturned yet ! "));
		bookTransactionHistory.setReturnApproved(true);
		return bookTransactionHistoryRepository.save(bookTransactionHistory).getId();
	}

	public void updateBookCoverPicture(MultipartFile file, Authentication connectedUser, Integer bookId) {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new EntityNotFoundException("No book found with the id " + bookId));

		User user = ((User) connectedUser.getPrincipal());

		String bookCover = fileStorageService.saveFile(file, user.getId());
		book.setBookCover(bookCover);
		bookRepository.save(book);

	}

}
