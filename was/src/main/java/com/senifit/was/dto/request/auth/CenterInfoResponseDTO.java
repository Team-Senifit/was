package com.senifit.was.dto.request.auth;

import com.senifit.was.entity.Instructors;
import com.senifit.was.entity.Members;
import com.senifit.was.entity.Programs;
import java.util.List;

public class CenterInfoResponseDTO {
    private String id;
    private String name;
    private String location;
    private List<Instructors> instructors;
    private List<Members> users;
    private List<Programs> programs;
}
