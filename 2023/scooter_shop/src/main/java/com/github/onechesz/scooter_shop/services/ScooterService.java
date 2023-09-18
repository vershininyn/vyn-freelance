package com.github.onechesz.scooter_shop.services;

import com.github.onechesz.scooter_shop.dtos.scooterdtos.ScooterDTIO;
import com.github.onechesz.scooter_shop.dtos.scooterdtos.ScooterDTOO;
import com.github.onechesz.scooter_shop.entities.ScooterEntity;
import com.github.onechesz.scooter_shop.repositories.ScooterRepository;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ScooterService {
    private final ScooterRepository scooterRepository;

    @Contract(pure = true)
    public ScooterService(ScooterRepository scooterRepository) {
        this.scooterRepository = scooterRepository;
    }

    public List<ScooterDTOO> findAll() {
        return scooterRepository.findAll().stream().map(ScooterEntity::convertToScooterDTOO).toList();
    }

    public List<ScooterDTOO> findAllExceptAlreadyUnderRent() {
        return scooterRepository.findAllExceptAlreadyUnderRent()
                .stream()
                .map(ScooterEntity::convertToScooterDTOO)
                .toList();
    }

    public boolean isScooterAlreadyExistsByTitle(String title) {
        return scooterRepository.isThisTitleAlreadyExists(title);
    }

    public Optional<ScooterDTOO> findById(int id) {
        return scooterRepository.findById(id).map(ScooterEntity::convertToScooterDTOO);
    }

    @Transactional
    public void editScooterParameters(int id, ScooterDTIO scooterDTIO) {
        scooterRepository.findById(id).ifPresent(scooterEntity -> {
            scooterEntity.setStartPrice(scooterDTIO.getStartPrice());
            scooterEntity.setRentPrice(scooterDTIO.getRentPrice());
            scooterEntity.setAlreadyUnderRent(false);
            scooterEntity.setTitle(scooterDTIO.getTitle());
            scooterEntity.setCharge(scooterDTIO.getCharge());

            scooterRepository.save(scooterEntity);
        });
    }

    @Transactional
    public void deleteScooter(int id) {
        scooterRepository.deleteById(id);
    }

    @Transactional
    public void add(@NotNull ScooterDTIO scooterDTIO) {
        scooterRepository.save(new ScooterEntity(
                                    scooterDTIO.getTitle(),
                                    scooterDTIO.getCharge(),
                                    scooterDTIO.getStartPrice(),
                                    scooterDTIO.getRentPrice(),
                    false));
    }
}
