package com.dxc.expense.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dxc.expense.dto.ExpenseRequestDTO;
import com.dxc.expense.dto.ExpenseResponseDTO;
import com.dxc.expense.exception.ResourceNotFoundException;
import com.dxc.expense.service.ExpenseServiceImpl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/expense")
@RequiredArgsConstructor
public class ExpenseController {

	private final ExpenseServiceImpl expenseService;
	
//	@PostMapping
//    public ResponseEntity<ExpenseResponseDTO> createExpense(@RequestBody @Valid ExpenseRequestDTO request) {
//        try {
//            ExpenseResponseDTO response = expenseService.createExpense(request);
//            return new ResponseEntity<>(response, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
	
	@PostMapping
    public ResponseEntity<ExpenseResponseDTO> createExpense(
            @RequestParam("amount") @NotNull(message = "Amount cannot be null") @Min(value = 1, message = "Amount must be a positive number") Double amount,
            @RequestParam("description") @NotBlank(message = "Description cannot be blank") String description,
            @RequestParam("category") @NotBlank(message = "Category cannot be blank") String category,
            @RequestParam("createdDate") LocalDateTime createdDate,
            @RequestParam("userId") @NotNull(message = "User ID cannot be null") Integer userId,
            @RequestParam(value = "receipt", required = false) MultipartFile receipt) {
        try {
            // Create an ExpenseRequestDTO from the request parameters
            ExpenseRequestDTO request = new ExpenseRequestDTO(amount, description, category, createdDate, userId, receipt);

            // Pass the DTO to the service layer
            ExpenseResponseDTO response = expenseService.createExpense(request);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping("/{expenseId}/receipt")
	public ResponseEntity<byte[]> downloadReceipt(@PathVariable Integer expenseId) {
	    try {
	        // Fetch the expense by ID
	        ExpenseResponseDTO expenseResponse = expenseService.getExpenseById(expenseId);

	        // Check if the receipt is present
	        if (expenseResponse.receipt() == null || expenseResponse.receipt().length == 0) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }

	        // Set the headers to download the file
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF);
	        headers.setContentDisposition(ContentDisposition.attachment().filename("receipt-" + expenseId + ".pdf").build());

	        // Return the file as a byte array
	        return new ResponseEntity<>(expenseResponse.receipt(), headers, HttpStatus.OK);
	    } catch (ResourceNotFoundException e) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> getAllExpenses() {
        try {
            List<ExpenseResponseDTO> response = expenseService.getAllExpenses();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping("/{expenseId}")
    public ResponseEntity<ExpenseResponseDTO> getExpenseById(@PathVariable Integer expenseId) {
        try {
            ExpenseResponseDTO response = expenseService.getExpenseById(expenseId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping("/user/{userId}")
    public ResponseEntity<List<ExpenseResponseDTO>> getAllExpensesByUserId(@PathVariable Integer userId) {
        try {
            List<ExpenseResponseDTO> response = expenseService.getAllExpensesByUserId(userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
}
