package com.UndangPurnama.CRUD_Mahasiswa.controller;

import com.UndangPurnama.CRUD_Mahasiswa.entity.Mahasiswa;
import com.UndangPurnama.CRUD_Mahasiswa.service.MahasiswaService;
import com.UndangPurnama.CRUD_Mahasiswa.util.ExcelGenerator;
import  com.UndangPurnama.CRUD_Mahasiswa.util.GeneratorPdfReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayInputStream;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class MahasiswaController {
    @GetMapping(value = "tambah")
    public String showTambahForm(Model model) {
        model.addAttribute("mahasiswa", new Mahasiswa());
        return "tambah";
    }

    @PostMapping(value = "tambah")
    public String tambahMahasiswaBaru(@ModelAttribute("mahasiswa") Mahasiswa mahasiswa) {
        MahasiswaService.saveMahasiswa(mahasiswa);
        return "redirect:/";
    }

    @Autowired
    private Mahasiswa mahasiswa;

    @Autowired
    private ExcelGenerator excelGenerator;

    @GetMapping
    public String index(Model model) {
        model.addAttribute(attributeName: "mahasiswas", MahasiswaService.getMahasiswas());
        return "index";
    }

    @GetMapping(value = "edit/{id}")
    public String editMahasiswa(Model model, @PathVariable("id") Integer id) {
        Mahasiswa mahasiswa = MahasiswaService.getMahasiswa(id);
        model.addAttribute("mahasiswa", mahasiswa);
        return "edit";
    }

    @PostMapping(value = "edit")
    public String updateMahasiswa(@ModelAttribute("mahasiswa") Mahasiswa mahasiswa) {
        MahasiswaService.updateMahasiswa(mahasiswa);
        return "redirect:/";
    }

    @GetMapping(value = "delete/{id}")
    public String deletingMahasiswa(@PathVariable("id") Integer id) {
        Mahasiswa mahasiswa = MahasiswaService.getMahasiswa(id);
        MahasiswaService.deleteMahasiswa(mahasiswa);
        return "redirect:/";
    }

    @GetMapping(value = "pdf",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> listMahasiswaReport() {

        List<Mahasiswa> MahasiswaList = MahasiswaService.getMahasiswas();

        ByteArrayInputStream bis = GeneratorPdfReport.listMahasiswasReport(MahasiswaList);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=List-Mahasiswa.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/xls")
    public ResponseEntity<InputStreamResource> excelMahasiswaReport() throws Exception {
        List<Mahasiswa> mahasiswaList = MahasiswaService.getMahasiswas();

        ByteArrayInputStream in = excelGenerator.exportExcel(mahasiswaList);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=List-Mahasiswa.xlsx");

        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));

    }
}

