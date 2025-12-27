package com.hiresense.repository;

import com.hiresense.model.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {

    Recruiter findByEmail(String email);

}
