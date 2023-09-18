package com.github.onechesz.scooter_shop.services;

import com.github.onechesz.scooter_shop.domain.ScooterHeartbeatObject;
import com.github.onechesz.scooter_shop.dtos.heartbeat.ScootersHeartbeatDTOO;
import com.github.onechesz.scooter_shop.dtos.rentdto.RentDTOO;
import com.github.onechesz.scooter_shop.entities.RentEntity;
import com.github.onechesz.scooter_shop.entities.ScooterEntity;
import com.github.onechesz.scooter_shop.entities.UserEntity;
import com.github.onechesz.scooter_shop.repositories.RentRepository;
import com.github.onechesz.scooter_shop.repositories.ScooterRepository;
import com.github.onechesz.scooter_shop.repositories.UserRepository;
import com.github.onechesz.scooter_shop.utils.JodaDateTimeWithTimeZoneUtil;
import org.jetbrains.annotations.Contract;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.Seconds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RentService {
    private final RentRepository rentRepository;
    private final UserRepository userRepository;
    private final ScooterRepository scooterRepository;
    private final JodaDateTimeWithTimeZoneUtil jodaDateTimeUtil;

    @Value("${scooter.shop.chargeDegradationAtSecondUnit}")
    private double chargeDegradationAtSecondUnit;

    @Contract(pure = true)
    public RentService(RentRepository rentRepository,
                       UserRepository userRepository,
                       ScooterRepository scooterRepository,
                       JodaDateTimeWithTimeZoneUtil jodaDateTimeUtil) {
        this.rentRepository = rentRepository;
        this.userRepository = userRepository;
        this.scooterRepository = scooterRepository;
        this.jodaDateTimeUtil = jodaDateTimeUtil;
    }

    public Optional<RentEntity> findByScooterId(int scooterId) {
        return rentRepository.findByScooterId(scooterId);
    }

    @Transactional
    public boolean start(int userId, int scooterId) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);
        Optional<ScooterEntity> scooterEntityOptional = scooterRepository.findById(scooterId);

        if (userEntityOptional.isPresent() && scooterEntityOptional.isPresent()) {
            UserEntity userEntity = userEntityOptional.get();
            ScooterEntity scooterEntity = scooterEntityOptional.get();

            BigDecimal totalPrice = scooterEntity.getStartPrice().add(scooterEntity.getRentPrice());

            if (userEntity.getBalance().compareTo(totalPrice) >= 0) {
                scooterEntity.setAlreadyUnderRent(true);
                scooterRepository.save(scooterEntity);

                rentRepository.save(new RentEntity(userEntity, scooterEntity, jodaDateTimeUtil.getDateTimeWithTimeZone()));

                return true;
            }

            return false;
        }

        return false;
    }

    @Transactional
    public ScootersHeartbeatDTOO getHMSScootersHeartbeatRentTime(int userId, final List<Integer> scooterIds) {
        Objects.requireNonNull(scooterIds);

        Map<Integer, ScooterHeartbeatObject> map = rentRepository
                .findByUserIdAndScooterIds(userId, scooterIds)
                .stream()
                .collect(Collectors.toMap(
                        rentEntity -> rentEntity.getScooterEntity().getId(),
                        rentEntity -> {
                            DateTime startDateTime = rentEntity.getStartTime(jodaDateTimeUtil);
                            Period nowRelativeToStartPeriod = jodaDateTimeUtil.computeRelativeToStartPeriod(startDateTime);
                            ScooterEntity scooter = rentEntity.getScooterEntity();

                            BigDecimal spentMoney = getSpentMoney(scooter, jodaDateTimeUtil.getDurationAtMinutes(startDateTime));

                            double currentCharge = scooter.getCharge();

                            if (currentCharge > 0.0) {
                                double diffAtSeconds = Seconds.standardSecondsIn(nowRelativeToStartPeriod).getSeconds();

                                currentCharge = Math.max(currentCharge - diffAtSeconds * chargeDegradationAtSecondUnit, 0.0);

                                scooter.setCharge(currentCharge);
                                scooterRepository.save(scooter);
                            }

                            long duration = jodaDateTimeUtil.getDurationAtMinutes(startDateTime);

                            return ScooterHeartbeatObject.builder()
                                    .startDateTime(startDateTime)
                                    .nowRelativeToStartPeriod(nowRelativeToStartPeriod)
                                    .spentMoney(spentMoney)
                                    .currentCharge(currentCharge)
                                    .userId(userId)
                                    .durationAtMinutes(duration)
                                    .build();
                        }));

        return new ScootersHeartbeatDTOO(map);
    }

    @Transactional
    public List<RentDTOO> findByUserId(int userId) {
        List<RentDTOO> rentDTOOS = new ArrayList<>();
//        LocalDateTime localDateTime = LocalDateTime.now();
        BigDecimal spentMoneySum = new BigDecimal(0);

        for (RentEntity rentEntity : rentRepository.findByUserId(userId)) {
            RentDTOO rentDTOO = RentEntity.convertToRentDTOO(rentEntity);
            DateTime startTime = rentEntity.getStartTime(jodaDateTimeUtil);

            long duration = jodaDateTimeUtil.getDurationAtMinutes(startTime);

            ScooterEntity scooterEntity = rentEntity.getScooterEntity();
            BigDecimal spentMoney = getSpentMoney(scooterEntity, duration);

            rentDTOO.setDurationAtMinutes(duration);
            rentDTOO.setSpentMoney(spentMoney);
            rentDTOO.setStartedDateAndTimeWithTimeZoneRentString(jodaDateTimeUtil.formatDateTimeWithTimeZoneToString(startTime));

            spentMoneySum = spentMoneySum.add(spentMoney);

            if (spentMoneySum.compareTo(rentDTOO.getUserEntity().getBalance()) > 0) {
                flushUserRents(userId);

                return Collections.emptyList();
            }

            rentDTOOS.add(rentDTOO);
        }

        return rentDTOOS;
    }

    private BigDecimal getSpentMoney(ScooterEntity scooterEntity, long duration) {
        return scooterEntity.getStartPrice().add(scooterEntity.getRentPrice().multiply(BigDecimal.valueOf(duration)));
    }

    private void flushUserRents(int userId) {
        rentRepository.deleteAllByUserId(userId);
        userRepository.setZeroBalance(userId);
    }

    @Transactional
    public void stop(int userId, int scooterId, long rentDuration) {
        scooterRepository
                .findById(scooterId)
                .ifPresent(scooterEntity -> userRepository
                        .findById(userId)
                        .ifPresent(userEntity -> {
                            BigDecimal addToSubstractValue = scooterEntity.getRentPrice().multiply(BigDecimal.valueOf(rentDuration));
                            BigDecimal substractFromNewBalance = scooterEntity.getStartPrice().add(addToSubstractValue);
                            BigDecimal newBalance = userEntity.getBalance().subtract(substractFromNewBalance);

                            userEntity.setBalance(newBalance);
                            userRepository.save(userEntity);

                            scooterEntity.setAlreadyUnderRent(false);
                            scooterRepository.save(scooterEntity);
                        })
                );

        rentRepository.deleteByUserIdAndScooterId(userId, scooterId);
    }
}
