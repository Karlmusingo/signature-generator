package com.ist.signature.services;

import com.ist.signature.dtos.response.UserResponse;
import com.ist.signature.exceptions.CustomException;
import com.ist.signature.models.entities.CompanyDetails;
import com.ist.signature.models.entities.User;
import com.ist.signature.repositories.CompanyDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyDetailsRepository companyRepository;

    public CompanyDetails getCompanyDetails() {
        List<CompanyDetails> cds = companyRepository.findAll();

        if(cds.isEmpty()) {
            return null;
        }

        return cds.get(0);
    }

    public CompanyDetails updateCompanyDetails(CompanyDetails data) {
        List<CompanyDetails> cds = companyRepository.findAll();

        CompanyDetails companyDetails;
        if(cds.isEmpty()) {
            companyDetails = new CompanyDetails();
        } else {
            companyDetails = cds.get(0);
        }

        Optional.ofNullable(data.getCompanyName()).ifPresent(companyDetails::setCompanyName);
        Optional.ofNullable(data.getMissionStatement()).ifPresent(companyDetails::setMissionStatement);
        Optional.ofNullable(data.getAddress()).ifPresent(companyDetails::setAddress);
        Optional.ofNullable(data.getWebsiteUrl()).ifPresent(companyDetails::setWebsiteUrl);

        companyRepository.save(companyDetails);

        return companyDetails;
    }
}
