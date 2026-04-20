package com.tlu.khoacntt.service;

import com.tlu.khoacntt.dto.response.MajorResponse;
import com.tlu.khoacntt.dto.response.TrainingProgramResponse;

import java.util.List;

public interface TrainingProgramService {

    List<TrainingProgramResponse> getAllForPublic();

    TrainingProgramResponse getById(Integer id);

    List<MajorResponse> getAllMajors();

    MajorResponse getMajorById(Integer id);
}
