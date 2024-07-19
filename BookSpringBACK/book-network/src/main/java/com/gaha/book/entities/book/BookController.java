package com.gaha.book.entities.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {

	@Autowired
	private BookService bookService;

	@PostMapping()
	public ResponseEntity<Integer> saveBook(@Valid @RequestBody BookRequest request, Authentication connectedUser) {
		return ResponseEntity.ok(bookService.save(request, connectedUser));
	}

	@GetMapping("{book-id}")
	public ResponseEntity<BookResponse> findBookById(@PathVariable("book-id") Integer bookId) {
		return ResponseEntity.ok(bookService.findById(bookId));
	}

	@GetMapping
	public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "size", defaultValue = "10", required = false) int size,
			Authentication connectedUser) {
		return ResponseEntity.ok(bookService.findAllBooks(page, size, connectedUser));
	}

	@GetMapping("/owner")
	public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "size", defaultValue = "10", required = false) int size,
			Authentication connectedUser) {
		return ResponseEntity.ok(bookService.findAllBooksByOwner(page, size, connectedUser));
	}

	@GetMapping("/borrowed")
	public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "size", defaultValue = "10", required = false) int size,
			Authentication connectedUser) {
		return ResponseEntity.ok(bookService.findAllBorrowedBooks(page, size, connectedUser));
	}

	@GetMapping("/returned")
	public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(
			@RequestParam(name = "page", defaultValue = "0", required = false) int page,
			@RequestParam(name = "size", defaultValue = "10", required = false) int size,
			Authentication connectedUser) {
		return ResponseEntity.ok(bookService.findAllReturnedBooks(page, size, connectedUser));
	}

	@PatchMapping("/shareable/{book-id}")
	public ResponseEntity<Integer> updateShareableStatus(@PathVariable("book-id") Integer bookId,
			Authentication connectedUser) {
		return ResponseEntity.ok(bookService.updateShareableStatus(bookId, connectedUser));
	}

	@PatchMapping("/archived/{book-id}")
	public ResponseEntity<Integer> updateArchivedStatus(@PathVariable("book-id") Integer bookId,
			Authentication connectedUser) {
		return ResponseEntity.ok(bookService.updateArchivedStatus(bookId, connectedUser));
	}

}
