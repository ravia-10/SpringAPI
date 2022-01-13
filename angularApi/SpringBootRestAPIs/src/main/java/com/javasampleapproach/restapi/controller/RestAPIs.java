package com.javasampleapproach.restapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javasampleapproach.restapi.model.Customer;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class RestAPIs {

	private Map<Long, Customer> customers = new HashMap<Long, Customer>(){
		
		private static final long serialVersionUID = 1L;

		{
	        put(1L, new Customer(1L, "Supreetha", "GC", 23));
	        put(2L, new Customer(2L, "Ravi", "A", 22));
	        put(3L, new Customer(3L, "bhoomika", "nadig", 22));
	        put(4L, new Customer(4L, "goomtesh", "patil", 22));
	       
	        
	    }
	};
	
	@GetMapping(value="/api/customers")
	public List<Customer> getAll(){
		List<Customer> results = customers.entrySet().stream()
									.map(entry ->entry.getValue())
									.collect(Collectors.toList());
		return results;
	}
	
	@GetMapping(value="/api/customers/{id}")
	public Customer getCustomer(@PathVariable Long id){
		return customers.get(id);
	}
	
	//add
	@PostMapping(value="/api/customers")
	public Customer postCustomer(@RequestBody Customer customer){
		Entry<Long, Customer> maxByKey = customers.entrySet()
                .stream()
                .reduce((curr, nxt) -> curr.getKey() > nxt.getKey() ? curr : nxt)
                .get();
		
		Long nextId = (long) (maxByKey.getKey() + 1);
		customer.setId(nextId);
		customers.put(nextId, customer);
		return customer;
	}
	
	
	@PutMapping(value="/api/customers")
	public void putCustomer(@RequestBody Customer customer){
		customers.replace(customer.getId(), customer);
	}
	
	//delete
	@DeleteMapping(value="/api/customers/{id}")
	public void deleteCustomer(@PathVariable Long id){
		customers.remove(id);
	}
}