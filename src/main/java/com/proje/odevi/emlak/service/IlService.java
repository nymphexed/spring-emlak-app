package com.proje.odevi.emlak.service;

import com.proje.odevi.emlak.model.Il;
import com.proje.odevi.emlak.repository.IlRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IlService {

    private final IlRepository ilRepository;

    public IlService(IlRepository ilRepository) {
        this.ilRepository = ilRepository;
    }

    public List<Il> findAll() {
        return ilRepository.findAll();
    }

    public Il save(Il il) {
        return ilRepository.save(il);
    }

    public Il findById(Long id) {
        return ilRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        ilRepository.deleteById(id);
    }

    public Object listele() {
       return ilRepository.findAll();
    }
}
