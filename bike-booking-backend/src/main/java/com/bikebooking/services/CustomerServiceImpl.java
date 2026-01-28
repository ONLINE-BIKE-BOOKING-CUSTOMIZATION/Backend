package com.bikebooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikebooking.dto.CustomerProfileResponseDTO;
import com.bikebooking.dto.CustomerProfileUpdateRequestDTO;
import com.bikebooking.entity.Address;
import com.bikebooking.entity.Customer;
import com.bikebooking.entity.User;
import com.bikebooking.repository.CustomerRepository;
import com.bikebooking.repository.UserRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private com.bikebooking.repository.AddressRepository addressRepository;

    @Override
    public CustomerProfileResponseDTO getProfile(Long customerUserId) {
        Customer customer = getCustomerFromUser(customerUserId);
        User user = customer.getUser();
        Address addr = customer.getDefaultAddress();

        return CustomerProfileResponseDTO.builder()
                .id(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone()) // User has phone
                .address(addr != null ? addr.getLine1() : "")
                .line2(addr != null ? addr.getLine2() : "")
                .city(addr != null ? addr.getCity() : "")
                .state(addr != null ? addr.getState() : "")
                .pincode(addr != null ? addr.getPincode() : "")
                .build();
    }

    @Override
    public void updateProfile(Long customerUserId, CustomerProfileUpdateRequestDTO request) {
        Customer customer = getCustomerFromUser(customerUserId);
        User user = customer.getUser();

        // Update User info
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        userRepository.save(user);

        // Update Address
        Address addr = customer.getDefaultAddress();
        if (addr == null) {
            addr = new Address();
            addr.setUser(user);
            customer.setDefaultAddress(addr);
        }
        addr.setLine1(request.getAddress());
        addr.setLine2(request.getLine2());
        addr.setCity(request.getCity());
        addr.setState(request.getState());
        addr.setPincode(request.getPincode());
        
        // Save Customer (cascades address usually, or save user/address explicitly if needed)
        // Since we modified user, we saved it. Address is linked to Customer? 
        // In Dealer, Address was OneToOne with Dealer. In Customer, it's OneToOne with DefaultAddress.
        
        // Save Customer (cascades address usually, or save user/address explicitly if needed)
        addressRepository.save(addr);
        customerRepository.save(customer); 
    }

    private Customer getCustomerFromUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return customerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Customer profile not found"));
    }
}
