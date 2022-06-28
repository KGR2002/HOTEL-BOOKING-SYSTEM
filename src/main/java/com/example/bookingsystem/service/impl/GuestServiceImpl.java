package com.example.bookingsystem.service.impl;

import com.example.bookingsystem.domain.Guest;
import com.example.bookingsystem.repository.GuestRepository;
import com.example.bookingsystem.service.GuestService;
import com.example.bookingsystem.validation.GuestValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class GuestServiceImpl implements GuestService {
    @Autowired
    private GuestRepository guestRepository;

    @Override
    public ResponseEntity<String> saveGuest(Guest guest) {
        log.debug("Guest: " + guest);

        if (GuestValidation.checkGuest(guest)) {
            log.error("Guest details incorrect");
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Guest details incorrect");
        }

        log.info("Saving guest: " + guest);
        guestRepository.save(guest);
        log.info("Guest saved in db");
        return ResponseEntity.ok().body("Guest saved.");
    }

    @Override
    public List<Guest> getAllGuests() {
        log.info("fetching all guests");
        List<Guest> allGuests = guestRepository.findAll();

        log.debug("All guests: " + allGuests);

        if (allGuests.isEmpty()) {
            log.error("no guest found in db");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "no guest found in db");
        }

        log.info("All guests fetched");
        return allGuests;
    }

}
