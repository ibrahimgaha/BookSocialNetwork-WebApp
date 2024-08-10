package com.gaha.book.entities.book;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory, Integer> {

	@Query("""
			SELECT h
			FROM BookTransactionHistory h
			WHERE h.user.id = :userId
			""")
	Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, Integer userId);

	@Query("""
			SELECT h
			FROM BookTransactionHistory h
			WHERE h.book.owner.id = :userId
			""")
	Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, Integer userId);

	@Query("""
			SELECT (COUNT(b) > 0)
			FROM BookTransactionHistory b
			WHERE b.user.id = :userId
			AND b.book.id = :bookId
			AND b.returnApproved = false
			""")
	boolean isAlreadyBorrowedByUser(Integer bookId, Integer userId);

	@Query("""
			SELECT transaction
			FROM BookTransactionHistory transaction
			WHERE transaction.user.id = :userId
			AND transaction.book.id = :bookId
			AND transaction.returned = false
			AND transaction.returnApproved = false
			""")
	Optional<BookTransactionHistory> findByBookIdAndUserId(Integer bookId, Integer userId);

	@Query("""
			SELECT transaction
			FROM BookTransactionHistory  transaction
			WHERE transaction.book.owner.id = :userId
			AND transaction.book.id = :bookId
			AND transaction.returned = true
			AND transaction.returnApproved = false
			""")
	Optional<BookTransactionHistory> findByBookIdAndOwnerId(Integer bookId, Integer userId);

}
