package ua.huryn.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.huryn.demo.entity.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
}
