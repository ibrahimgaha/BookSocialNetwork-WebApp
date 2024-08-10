package com.gaha.book.entities.FeedBack;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.gaha.book.entities.book.Book;

@Component
public class FeedBackMapper {

	public FeedBack toFeedBack(FeedBackRequest request) {
		return FeedBack.builder().note(request.note()).comment(request.comment())
				.book(Book.builder().id(request.bookId()).build()).build();
	}

	public FeedBackResponse toFeedBackResponse(FeedBack feedBack, Integer id) {
		return FeedBackResponse.builder().note(feedBack.getNote()).comment(feedBack.getComment())
				.ownFeedBack(Objects.equals(feedBack.getCreatedBy(), id)).build();
	}
}
