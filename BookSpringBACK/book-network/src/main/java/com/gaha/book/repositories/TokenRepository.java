package com.gaha.book.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gaha.book.entities.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

	Optional<Token> findByToken(String token);
}
