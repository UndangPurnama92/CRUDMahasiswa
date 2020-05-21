package com.UndangPurnama.CRUD_Mahasiswa.repository;

import com.UndangPurnama.CRUD_Mahasiswa.entity.Mahasiswa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MahasiswaRepository extends JpaRepository<Mahasiswa, Integer>{
}
