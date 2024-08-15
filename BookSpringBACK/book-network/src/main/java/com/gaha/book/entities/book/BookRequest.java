package com.gaha.book.entities.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BookRequest(Integer id,

		@NotEmpty(message = "100") @NotNull(message = "100") String title,

		@NotEmpty(message = "101") @NotNull(message = "101") String authorName,

		@NotEmpty(message = "102") @NotNull(message = "102") String isbn,

		@NotEmpty(message = "104") @NotNull(message = "104") String synopsis,

		Boolean shareable) {
}
