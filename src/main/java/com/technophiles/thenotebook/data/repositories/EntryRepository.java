package com.technophiles.thenotebook.data.repositories;

import com.technophiles.thenotebook.data.models.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {

}
