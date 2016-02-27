package com.pharmacy.service.api;

import com.pharmacy.domain.Pharmacy;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Alexander on 14.11.2015.
 */
public interface ImportService {

    void importCSVFile(MultipartFile file, Pharmacy pharmacy);
}
