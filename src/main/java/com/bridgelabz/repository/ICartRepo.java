package com.bridgelabz.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.ResponseEntity;
import com.bridgelabz.model.CartModel;

@Repository
public interface ICartRepo extends JpaRepository<CartModel, Long> {

	void save(ResponseEntity cartDetails);

}