package com.UndangPurnama.CRUD_Mahasiswa.service;

import com.UndangPurnama.CRUD_Mahasiswa.entity.Mahasiswa;
import com.UndangPurnama.CRUD_Mahasiswa.exception.ResourceNotFoundException;
import com.UndangPurnama.CRUD_Mahasiswa.repository.MahasiswaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MahasiswaServicelmpl implements MahasiswaService{
    @Autowired
    private MahasiswaRepository mahasiswaRepository;

    @Override
    public List<Mahasiswa> getMahasiswas() {return mahasiswaRepository.findAll();}

    @Override
    public Mahasiswa getMahasiswa(Integer id) {
        return mahasiswaRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Mahasiswa [mahasiswaId=" + id + "] " +
                "can't be found"));
    }

    @Override
    public Mahasiswa saveMahasiswa(Mahasiswa mahasiswa) { return mahasiswaRepository.save(mahasiswa);}

    @Override
    public Mahasiswa updateMahasiswa(Mahasiswa mahasiswa) {
        return mahasiswaRepository.findAllById(mahasiswa.getId()).map(mahasiswaTemp ->{
            mahasiswaTemp.setId(mahasiswa.getId());
            mahasiswaTemp.setNim(mahasiswa.getNIM());
            mahasiswaTemp.setNamaMahasiswa(mahasiswa.getNamaMahasiswa());
            mahasiswaRepository.save(mahasiswaTemp);
            return mahasiswaTemp;
        }).orElseThrow(()-> new ResourceNotFoundException("Mahasiswa [mahasiswaId=" + Mahasiswa.getId() + "] can't be found"));
    }

    @Override
    public void deleteMahasiswa(Mahasiswa mahasiswa) {
        mahasiswaRepository.findById(mahasiswa.getId()).orElseThrow(()-> new ResourceNotFoundException("Mahasiswa" +
                "[mahasiswaId=" + mahasiswa.getId() + "] can't be found"));
        mahasiswaRepository.deleteById(mahasiswa.getId());

    }
}
