package com.login.login.Service;

import com.login.login.Model.User;
import com.login.login.Repository.ResidentRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ResidentServiceImpl implements ResidentService {

    private final ResidentRepository residentRepository;

    public ResidentServiceImpl(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    @Override
    public Page<User> getResidents(int page, int pageSize) {
        return residentRepository.findAll(PageRequest.of(page, pageSize));
    }

    @Override
    public Page<User> searchResidents(String name, String number, int page, int pageSize) {
        return residentRepository.findByNameContainingAndNumberContaining(name, number, PageRequest.of(page, pageSize));
    }

    @Override
    public long getTotalResidentCount() {
        return residentRepository.count();
    }

    @Override
    public void addResident(User user) {
        residentRepository.save(user);
    }

    @Override
    public boolean existsByNumber(String number) {
        return residentRepository.existsByNumber(number);
    }

    @Override
    public User findByNumber(String number) {
        return residentRepository.findByNumber(number).orElse(null);
    }

    @Override
    public void updateResident(User user) {
        User existingResident = residentRepository.findByNumber(user.getNumber()).orElse(null);

        if (existingResident != null) {
            existingResident.setName(user.getName());
            existingResident.setAddress(user.getAddress());

            residentRepository.save(existingResident);
        }
    }

    @Override
    public void deleteResident(String number) {
        residentRepository.deleteByNumber(number);
    }
}
