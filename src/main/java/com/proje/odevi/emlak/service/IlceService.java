package com.proje.odevi.emlak.service;

import com.proje.odevi.emlak.model.Ilce;
import com.proje.odevi.emlak.repository.IlceRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IlceService {

    private final IlceRepository ilceRepository;

    public List<Ilce> findByIlId(Long ilId) {
        return ilceRepository.findByIlId(ilId);
    }
    public IlceService(IlceRepository ilceRepository) {
        this.ilceRepository = ilceRepository;
    }

    public List<Ilce> findAll() {
        return ilceRepository.findAll();
    }

    public Ilce save(Ilce ilce) {
        return ilceRepository.save(ilce);
    }

    public Ilce findById(Long id) {
        return ilceRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        ilceRepository.deleteById(id);
    }
}
