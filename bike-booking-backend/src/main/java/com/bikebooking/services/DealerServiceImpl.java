package com.bikebooking.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikebooking.dto.BookingResponseDTO;
import com.bikebooking.entity.Booking;
import com.bikebooking.entity.Dealer;
import com.bikebooking.entity.User;
import com.bikebooking.enums.BookingStatus;
import com.bikebooking.repository.*;

@Service
@org.springframework.transaction.annotation.Transactional
public class DealerServiceImpl implements DealerService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private BikeRepository bikeRepository;

    @Autowired
    private DealerBikeRepository dealerBikeRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<com.bikebooking.dto.ShowroomBikeResponseDTO> getInventory(Long dealerUserId) {
        Dealer dealer = getDealerFromUser(dealerUserId);
        
        List<com.bikebooking.entity.DealerBike> inventory = dealerBikeRepository.findByDealer(dealer);

        return inventory.stream().map(db -> com.bikebooking.dto.ShowroomBikeResponseDTO.builder()
                .dealerBikeId(db.getDealerBikeId())
                .dealerId(dealer.getDealerId())
                .showroomName(dealer.getShowroomName())
                .city(dealer.getAddress().getCity())
                .address(dealer.getAddress().getLine1())
                .bikeId(db.getBike().getBikeId())
                .bikeName(db.getBike().getName())
                .brand(db.getBike().getBrand())
                .price(db.getPrice())
                .stock(db.getStock())
                .offer(db.getOffer())
                .build())
                .collect(Collectors.toList());
    }

    @Override
    public void addBikeToInventory(Long dealerUserId, com.bikebooking.dto.DealerInventoryRequestDTO request) {
        Dealer dealer = getDealerFromUser(dealerUserId);
        
        com.bikebooking.entity.Bike bike = bikeRepository.findById(request.getBikeId())
                .orElseThrow(() -> new RuntimeException("Bike not found"));

        // Check if already exists
        if (dealerBikeRepository.existsByDealerAndBike(dealer, bike)) {
            throw new RuntimeException("Bike already in your showroom");
        }

        // DEBUGGING START
        System.out.println("DEBUG: Starting Add Bike. DealerID: " + dealerUserId);
        
        try {
            com.bikebooking.entity.DealerBike newEntry = new com.bikebooking.entity.DealerBike();
            newEntry.setDealer(dealer);
            newEntry.setBike(bike);
            newEntry.setPrice(request.getPrice());
            newEntry.setStock(request.getStock());
            newEntry.setOffer(request.getOffer());
            newEntry.setAvailable(true);
            newEntry.setIsDeleted(false); // Manually set if setter exists, otherwise ignore

            System.out.println("DEBUG: Saving new entry...");
            dealerBikeRepository.save(newEntry);
            System.out.println("DEBUG: Saved successfully!");
            
        } catch (Exception e) {
            System.err.println("CRITICAL FAILURE IN ADD BIKE:");
            e.printStackTrace();
            throw new RuntimeException("Save Failed: " + e.getMessage());
        }
    }

    @Override
    public void updateInventory(Long dealerBikeId, com.bikebooking.dto.DealerInventoryRequestDTO request) {
        com.bikebooking.entity.DealerBike entry = dealerBikeRepository.findById(dealerBikeId)
                .orElseThrow(() -> new RuntimeException("Inventory item not found"));

        entry.setPrice(request.getPrice());
        entry.setStock(request.getStock());
        entry.setOffer(request.getOffer());

        dealerBikeRepository.save(entry);
    }
    
    // Helper
    private Dealer getDealerFromUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return dealerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Dealer profile not found"));
    }

    @Override
    public List<BookingResponseDTO> getPendingBookings(Long dealerUserId) {
        
        // Find Dealer Profile from UserID
        User user = userRepository.findById(dealerUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        Dealer dealer = dealerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Dealer profile not found"));

        List<Booking> bookings = bookingRepository.findByDealerAndStatus(dealer, BookingStatus.PENDING);

        return bookings.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public void acceptBooking(Long bookingId, java.time.LocalDate deliveryDate) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        if (booking.getStatus() != BookingStatus.PENDING) {
             throw new RuntimeException("Only pending bookings can be accepted");
        }

        // 🔥 INVENTORY CHECK & DECREMENT (Same logic as BookingServiceImpl to ensure consistency) 🔥
        com.bikebooking.entity.DealerBike dealerBike = dealerBikeRepository
                .findByDealerAndBike(booking.getDealer(), booking.getBike())
                .orElseThrow(() -> new RuntimeException("This bike is not in your inventory list!"));

        if (dealerBike.getStock() <= 0) {
            throw new RuntimeException("Cannot accept booking! Stock is 0.");
        }

        // Decrement stock
        dealerBike.setStock(dealerBike.getStock() - 1);
        dealerBikeRepository.save(dealerBike);

        // Update Booking
        booking.setStatus(BookingStatus.ACCEPTED);
        booking.setDeliveryDate(deliveryDate);
        bookingRepository.save(booking);
    }

    @Override
    public void rejectBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        
        booking.setStatus(BookingStatus.REJECTED);
        bookingRepository.save(booking);
    }

    

    @Override
    public com.bikebooking.dto.DealerStatsDTO getDealerStats(Long dealerUserId) {
        Dealer dealer = getDealerFromUser(dealerUserId);
        
        // Fetch ALL bookings for this dealer
        List<Booking> allBookings = bookingRepository.findByDealer(dealer);
        
        double revenue = 0;
        int pending = 0;
        int accepted = 0;
        int rejected = 0;

        for (Booking b : allBookings) {
            switch (b.getStatus()) {
                case PENDING:
                    pending++;
                    break;
                case ACCEPTED: // Sales considered closed for now
                    accepted++; 
                    revenue += b.getTotalAmount();
                    break;
                case REJECTED:
                    rejected++;
                    break;
                default:
                    break;
            }
        }

        return com.bikebooking.dto.DealerStatsDTO.builder()
                .totalRevenue(revenue)
                .totalBookings(allBookings.size())
                .pendingBookings(pending)
                .acceptedBookings(accepted)
                .rejectedBookings(rejected)
                .totalBikesSold(accepted) // Assuming Accepted = Sold for MVP
                .build();
    }

    @Autowired
    private PaymentRepository paymentRepository;

    private BookingResponseDTO mapToDTO(Booking b) {
        // Fetch financial info
        com.bikebooking.entity.Payment payment = paymentRepository.findByBooking(b);
        double paid = payment != null ? payment.getPaidAmount() : 0;
        double remaining = payment != null ? payment.getRemainingAmount() : b.getTotalAmount();

        return BookingResponseDTO.builder()
                .bookingId(b.getBookingId())
                .bikeName(b.getBike().getName())
                .customerName(b.getCustomer().getUser().getName())
                .status(b.getStatus())
                .totalAmount(b.getTotalAmount())
                .paidAmount(paid)
                .remainingAmount(remaining)
                .deliveryDate(b.getDeliveryDate() != null ? b.getDeliveryDate().toString() : "TBD")
                .createdAt(b.getCreatedAt().toString())
                .build();
    }
    @Override
    public com.bikebooking.dto.DealerResponseDTO getProfile(Long dealerUserId) {
        Dealer dealer = getDealerFromUser(dealerUserId);
        
        return com.bikebooking.dto.DealerResponseDTO.builder()
                .dealerId(dealer.getDealerId())
                .showroomName(dealer.getShowroomName())
                .gstNumber(dealer.getGstNumber())
                .contactNumber(dealer.getContactNumber())
                .city(dealer.getAddress() != null ? dealer.getAddress().getCity() : "")
                .state(dealer.getAddress() != null ? dealer.getAddress().getState() : "")
                .pincode(dealer.getAddress() != null ? dealer.getAddress().getPincode() : "")
                .pincode(dealer.getAddress() != null ? dealer.getAddress().getPincode() : "")
                .address(dealer.getAddress() != null ? dealer.getAddress().getLine1() : "")
                .line2(dealer.getAddress() != null ? dealer.getAddress().getLine2() : "")
                .ownerName(dealer.getUser().getName())
                .ownerEmail(dealer.getUser().getEmail())
                .verified(dealer.getVerified())
                .build();
    }

    @Override
    public void updateProfile(Long dealerUserId, com.bikebooking.dto.DealerProfileUpdateRequestDTO request) {
        Dealer dealer = getDealerFromUser(dealerUserId);
        User user = dealer.getUser();

        // Update User info
        user.setName(request.getName());
        // user.setPhone(request.getPhone()); // Sync phone if needed, skipping for now to avoid unique constraint issues if unchanged or logic complexity
        
        // Update Dealer info
        dealer.setContactNumber(request.getPhone());

        // Update Address (Simple Line 1 update for now as per DTO)
        com.bikebooking.entity.Address address = dealer.getAddress();
        if (address == null) {
            address = new com.bikebooking.entity.Address();
            address.setUser(user); // If 1-to-1 logic
            dealer.setAddress(address);
        }
        address.setLine1(request.getAddress());
        address.setLine2(request.getLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPincode(request.getPincode());
        
        
        // Save cascade usually handles this if configured, but let's save explicitly to be safe
        userRepository.save(user);
        addressRepository.save(address); 
        dealerRepository.save(dealer);
    }
}
