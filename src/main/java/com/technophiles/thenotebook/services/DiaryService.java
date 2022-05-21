package com.technophiles.thenotebook.services;

import com.technophiles.thenotebook.data.models.Diary;
import com.technophiles.thenotebook.data.models.User;

public interface DiaryService {

    Diary createDiary(String title, User user);
}
