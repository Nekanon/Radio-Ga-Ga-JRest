package com.starlabs.RadioGaGa.repo;

import com.starlabs.RadioGaGa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
}
