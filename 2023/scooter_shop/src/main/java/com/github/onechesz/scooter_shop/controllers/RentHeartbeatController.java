package com.github.onechesz.scooter_shop.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.onechesz.scooter_shop.dtos.heartbeat.ScootersHeartbeatDTOO;
import com.github.onechesz.scooter_shop.dtos.rentdto.HMSScooterHeartbeatRentDTIO;
import com.github.onechesz.scooter_shop.security.UserDetailsImpl;
import com.github.onechesz.scooter_shop.services.RentService;
import org.jetbrains.annotations.Contract;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(path = "/heartbeat_rents", produces = MediaType.APPLICATION_JSON_VALUE)
public class RentHeartbeatController {
    private final RentService rentService;

    @Contract(pure = true)
    public RentHeartbeatController(RentService rentService) {
        this.rentService = rentService;
    }

    @PostMapping(path = "/{scooter_id}/stop")
    @ResponseBody
    public ResponseEntity<HttpStatus> stopScooterRent(@PathVariable(name = "scooter_id") int scooterId,
                                                      @RequestParam(name = "user_id") int userId,
                                                      @RequestParam(name = "rent_duration") long rentDuration) {
        rentService.stop(userId, scooterId, rentDuration);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping(path = "/{user_id}/getHMSScootersHeartbeatRentTime", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ScootersHeartbeatDTOO>
    getHMSScootersHeartbeatRentTime(@PathVariable(name = "user_id") int user_id,
                                    @RequestBody HMSScooterHeartbeatRentDTIO scootersLongIds) throws JsonProcessingException {
        checkScooterLongIds(scootersLongIds);

        ScootersHeartbeatDTOO obj
                = rentService.getHMSScootersHeartbeatRentTime(user_id, scootersLongIds.getScooters_long_ids());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(obj);
    }

    @PostMapping(path = "/getHMSScootersHeartbeatRentTime", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ScootersHeartbeatDTOO>
    getHMSScootersHeartbeatRentTime(@RequestBody HMSScooterHeartbeatRentDTIO scootersLongIds) throws JsonProcessingException {
        checkScooterLongIds(scootersLongIds);

        int user_id = ((UserDetailsImpl) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId();

        return getHMSScootersHeartbeatRentTime(user_id, scootersLongIds);
    }

    private void checkScooterLongIds(HMSScooterHeartbeatRentDTIO scootersLongIds) {
        Objects.requireNonNull(scootersLongIds);

        if (scootersLongIds.getScooters_long_ids().isEmpty()) {
            throw new IllegalArgumentException("scootersLongIds is EMPTY");
        }
    }
}
