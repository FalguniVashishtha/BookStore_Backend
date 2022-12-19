package com.bridgelabz.dto;

public class CartDto {
	Long cartId;
	Long userId;
	Long bookId;
	int quantity;

	public CartDto() {
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getCartId() {
		return cartId;
	}
	
	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


}
