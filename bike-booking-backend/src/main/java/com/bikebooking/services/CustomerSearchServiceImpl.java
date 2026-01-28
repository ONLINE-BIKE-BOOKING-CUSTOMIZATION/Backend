package com.bikebooking.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikebooking.dto.ShowroomBikeResponseDTO;
import com.bikebooking.entity.DealerBike;
import com.bikebooking.repository.DealerBikeRepository;

@Service
public class CustomerSearchServiceImpl implements CustomerSearchService {

    @Autowired
    private DealerBikeRepository dealerBikeRepository;

    @Override
    public List<ShowroomBikeResponseDTO> searchBikes(String city, String bikeName) {

        List<DealerBike> results =
                dealerBikeRepository.searchBikesByCityAndName(city, bikeName);

        return results.stream()
                .map(db -> ShowroomBikeResponseDTO.builder()
                        .dealerId(db.getDealer().getDealerId())
                        .showroomName(db.getDealer().getShowroomName())
                        .city(db.getDealer().getAddress().getCity())
                        .address(
                                db.getDealer().getAddress().getLine1() + ", " +
                                db.getDealer().getAddress().getLine2()
                        )
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
    public List<ShowroomBikeResponseDTO> getDealersForBike(Long bikeId) {
        List<DealerBike> results = dealerBikeRepository.findDealersByBikeId(bikeId);

        return results.stream()
                .map(db -> ShowroomBikeResponseDTO.builder()
                        .dealerId(db.getDealer().getDealerId())
                        .showroomName(db.getDealer().getShowroomName())
                        .city(db.getDealer().getAddress().getCity())
                        .address(
                                db.getDealer().getAddress().getLine1() + ", " +
                                db.getDealer().getAddress().getLine2()
                        )
                        .bikeId(db.getBike().getBikeId())
                        .bikeName(db.getBike().getName())
                        .brand(db.getBike().getBrand())
                        .price(db.getPrice())
                        .stock(db.getStock())
                        .offer(db.getOffer())
                        .build())
                .collect(Collectors.toList());
    }
}
