package com.gaha.book.entities.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {

	@Query("""
			SELECT book
			From Book book
			WHERE book.archived = false
			AND book.shareable = true
			AND book.owner.id != :userId
			""")

	Page<Book> findAllDisplayableBooks(Pageable pageable, Integer userId);

}
