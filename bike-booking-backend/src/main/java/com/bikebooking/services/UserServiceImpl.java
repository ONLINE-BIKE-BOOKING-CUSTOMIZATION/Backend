package com.bikebooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bikebooking.dto.LoginRequest;
import com.bikebooking.dto.LoginResponse;
import com.bikebooking.dto.RegisterRequest;
import com.bikebooking.entity.*;
import com.bikebooking.enums.Role;
import com.bikebooking.enums.UserStatus;
import com.bikebooking.repository.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // ================= REGISTER USER =================

    @Override
    @org.springframework.transaction.annotation.Transactional
    public String registerUser(RegisterRequest request) {

        // ... (User logic remains same) ...
        // Check duplicate email
        java.util.Optional<User> existing = userRepository.findByEmail(request.getEmail());
        
        User user;

        if (existing.isPresent()) {
            User found = existing.get();
            if (found.getIsDeleted()) {
                // REACTIVATE
                user = found;
                user.setIsDeleted(false);
                user.setStatus(UserStatus.ACTIVE);
            } else {
                throw new RuntimeException("Email already registered");
            }
        } else {
            // CREATE NEW
            user = User.builder().build();
        }

        // Set/Update Fields
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        if (user.getStatus() == null) user.setStatus(UserStatus.ACTIVE);
        
        User savedUser = userRepository.save(user);

        // 🔥 2️⃣ CREATE ADDRESS (COMMON FOR DEALER & CUSTOMER)

        Address address = Address.builder()
                .line1(request.getLine1())
                .line2(request.getLine2())
                .city(request.getCity())
                .state(request.getState())
                .pincode(request.getPincode())
                .user(savedUser)        // one user → one address
                .build();

        Address savedAddress = addressRepository.save(address);

        // 🔥 3️⃣ ROLE SPECIFIC CREATION

        if (request.getRole() == Role.DEALER) {
             // ... (Dealer logic) ...
            java.util.Optional<Dealer> existingDealer = dealerRepository.findByUser(savedUser);
            
            Dealer dealer;
            if (existingDealer.isPresent()) {
                dealer = existingDealer.get();
                dealer.setIsDeleted(false);
            } else {
                dealer = Dealer.builder().user(savedUser).build();
            }

            dealer.setShowroomName(request.getShowroomName());
            dealer.setGstNumber(request.getGstNumber());
            dealer.setContactNumber(request.getContactNumber());
            dealer.setVerified(false); // Reset verification on re-register
            dealer.setAddress(savedAddress);
            
            dealerRepository.save(dealer);

            return "Dealer registered successfully. Waiting for admin verification.";

        } 
        else if (request.getRole() == Role.CUSTOMER) {
            System.out.println("🔥 [Register] Creating CUSTOMER Profile for User: " + savedUser.getName());

            // Customer profile (NO verification at all)
            // 🔥 CUSTOMER REACTIVATION FIX
            java.util.Optional<Customer> existingCustomer = customerRepository.findByUser_UserId(savedUser.getUserId());
            
            Customer customer;
            if (existingCustomer.isPresent()) {
                System.out.println("   -> Found existing customer, reactivating.");
                customer = existingCustomer.get();
                // If Customer entity has isDeleted, set it false. BaseEntity usually has it.
                 customer.setIsDeleted(false);
            } else {
                System.out.println("   -> Creating NEW customer entity.");
                customer = Customer.builder()
                    .user(savedUser)
                    .loyaltyPoints(0)
                    .build();
            }
            
            customer.setDefaultAddress(savedAddress);

            Customer savedCust = customerRepository.save(customer);
            System.out.println("✅ Customer Saved! ID: " + savedCust.getCustomerId() + " (Linked User ID: " + savedUser.getUserId() + ")");

            return "Customer registered successfully.";

        } 
        else {
            // ADMIN registration (no extra table, auto active)

            return "Admin registered successfully.";
        }
    }


    // ================= LOGIN USER =================

    @Override
    public LoginResponse loginUser(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        // Check password
        boolean match = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!match) {
            throw new RuntimeException("Invalid password");
        }

        // 🔥 DEALER VERIFICATION CHECK (FROM USER TABLE ONLY)
        Boolean verified = null;

        if (user.getRole() == Role.DEALER) {
            Dealer dealer = dealerRepository.findByUser(user).get();
            verified = dealer.getVerified();
        }

        return LoginResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .verified(verified)
                .message("Login successful")
                .build();

    }
    }
