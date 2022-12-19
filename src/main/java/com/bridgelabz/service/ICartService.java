package com.bridgelabz.service;
import java.util.List;

import com.bridgelabz.ResponseEntity;
import com.bridgelabz.dto.CartDto;

public interface ICartService {

	CartDto add(CartDto cartDto);
	
	ResponseEntity addBook(CartDto cartDto);

	List<CartDto> findAll();

	CartDto findById(Long id);

	void deleteById(Long id);

	CartDto updateCartData(Long id, CartDto cartDto);

}