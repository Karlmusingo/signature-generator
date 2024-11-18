package com.ist.signature.repositories;

import com.ist.signature.models.entities.CompanyDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CompanyDetailsRepository extends JpaRepository<CompanyDetails, UUID> {
}
