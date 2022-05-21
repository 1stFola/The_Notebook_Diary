package com.technophiles.thenotebook.data.repositories;

import com.technophiles.thenotebook.data.models.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

}
