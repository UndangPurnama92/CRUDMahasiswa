package com.UndangPurnama.CRUD_Mahasiswa.service;

import com.UndangPurnama.CRUD_Mahasiswa.entity.Mahasiswa;

import java.util.List;

public interface MahasiswaService {
    List<Mahasiswa> getMahasiswas();

    Mahasiswa getMahasiswa(Integer id);

    Mahasiswa saveMahasiswa(Mahasiswa mahasiswa);

    Mahasiswa updateMahasiswa(Mahasiswa mahasiswa);

    void deleteMahasiswa(Mahasiswa mahasiswa);
}