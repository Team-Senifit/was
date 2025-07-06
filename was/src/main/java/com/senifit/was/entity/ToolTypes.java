package com.senifit.was.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ToolTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long toolTypesId;

    Tools tools;

    @OneToMany(mappedBy = "exercisesToolTypes", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExercisesToolTypes> exercisesToolTypes;
}
