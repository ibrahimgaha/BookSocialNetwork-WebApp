package com.gaha.book.entities.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory, Integer> {

	@Query("""
			SELECT history
			FROM BookTransactionHistory h
			WHERE h.user.id = : userId
			""")
	Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, Integer userId);

	@Query("""
			SELECT history
			FROM BookTransactionHistory h
			WHERE h.book.owner.id = : userId
			""")
	Page<BookTransactionHistory> findAllReturnedBooks(Pageable pageable, Integer userId);

}
