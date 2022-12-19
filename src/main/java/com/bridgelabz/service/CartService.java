package com.bridgelabz.service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.ResponseEntity;
import com.bridgelabz.dto.CartDto;
import com.bridgelabz.dto.UserDto;
import com.bridgelabz.exception.BookException;
import com.bridgelabz.model.BookModel;
import com.bridgelabz.model.CartModel;
import com.bridgelabz.model.UserModel;
import com.bridgelabz.repository.IBookRepo;
import com.bridgelabz.repository.ICartRepo;
import com.bridgelabz.repository.IUserRepo;

@Service
public class CartService implements ICartService {

	@Autowired
	ICartRepo cartRepo;

	@Autowired
	IBookRepo bookRepo;

	@Autowired
	IUserRepo userRepo;

	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public CartDto add(CartDto cartDto) {
		
		CartModel cart = modelMapper.map(cartDto,CartModel.class);
		cartRepo.save(cart);
		return cartDto;
		}

	@Override
	public ResponseEntity addBook(CartDto cartDto) {
		Optional<UserModel> user = userRepo.findById(cartDto.getUserId());
		Optional<BookModel> book = bookRepo.findById(cartDto.getBookId());
		if (user.isPresent() && book.isPresent()) {
			CartModel cartDetails = new CartModel(user.get(), book.get(), cartDto.getQuantity());
			cartRepo.save(cartDetails);
			return new ResponseEntity(cartDetails, "Book added succesfully");
		} else {
			throw new BookException(" UserId and BookId  is Invalid");
		}
	}

	@Override
	public List<CartDto> findAll() {
		return cartRepo.findAll().stream().map(cart -> modelMapper.map(cart, CartDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public CartDto findById(Long id) {
		Optional<CartModel> cart = cartRepo.findById(id);
		if (cart.isEmpty()) {
			throw new BookException(" Cart Id does not exist");
		}
		CartDto cartDto = modelMapper.map(cart.get(), CartDto.class);
		return cartDto;
	}

	@Override
	public void deleteById(Long id) {
		if (cartRepo.findById(id).isPresent()) {
			cartRepo.deleteById(id);
		} else {
			throw new BookException("Id is not valid");
		}
	}

	@Override
	public CartDto updateCartData(Long id, CartDto cartDto) {
		Optional<BookModel> book = bookRepo.findById(cartDto.getBookId());
		CartModel editCart = cartRepo.findById(id).orElse(null);
		if (editCart != null) {
			CartModel cart = modelMapper.map(cartDto, CartModel.class);
			editCart.setBook(book.get());
			editCart.setQuantity(cartDto.getQuantity());
			cartRepo.save(editCart);
			} else {
			throw new BookException("Id and quantity are not present");
		}
		return cartDto;
	}


}
