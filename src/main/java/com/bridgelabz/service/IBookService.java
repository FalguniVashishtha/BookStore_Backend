package com.bridgelabz.service;

import java.util.List;

import com.bridgelabz.dto.BookDto;

public interface IBookService {

	BookDto addBook(BookDto bookDto);
	List<BookDto> getAllBook();

	BookDto findById(Long bookId);

	void deleteById(Long bookId);

	BookDto findByBookName(String bookName);
	BookDto updateData(Long bookId, BookDto bookDto);
}
