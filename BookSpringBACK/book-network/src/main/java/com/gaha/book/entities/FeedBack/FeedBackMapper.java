package com.gaha.book.entities.FeedBack;

import java.util.Objects;

import com.gaha.book.entities.book.Book;

public class FeedBackMapper {

	public FeedBack toFeedBack(FeedBackRequest request) {
		// TODO Auto-generated method stub
		return FeedBack.builder().note(request.note()).comment(request.comment())
				.book(Book.builder().id(request.bookId()).build())

				.build();
	}

	public FeedBackResponse toFeedBackResponse(FeedBack feedBack, Integer id) {
		// TODO Auto-generated method stub
		return FeedBackResponse.builder().note(feedBack.getNote()).comment(feedBack.getComment())
				.ownFeedBack(Objects.equals(feedBack.getCreatedBy(), id)).build();
	}
}
