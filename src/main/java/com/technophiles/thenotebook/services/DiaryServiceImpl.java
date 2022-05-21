package com.technophiles.thenotebook.services;

import com.technophiles.thenotebook.data.models.Diary;
import com.technophiles.thenotebook.data.models.User;
import com.technophiles.thenotebook.data.repositories.DiaryRepository;
import org.springframework.stereotype.Service;


@Service
public class DiaryServiceImpl implements DiaryService{
    private DiaryRepository diaryRepository;

    public DiaryServiceImpl(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Override
    public Diary createDiary(String title, User user) {
        Diary diary = new Diary(title);
        diary.setUser(user);
        return diaryRepository.save(diary);
    }
}
