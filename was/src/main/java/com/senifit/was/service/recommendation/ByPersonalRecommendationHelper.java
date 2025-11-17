package com.senifit.was.service.recommendation;

import com.senifit.was.entity.Program;
import com.senifit.was.entity.ProgramVideo;
import com.senifit.was.entity.ProgramVideoId;
import com.senifit.was.entity.lookup.*;
import com.senifit.was.util.TsidIdGenerator;
import com.senifit.was.vo.ProgramData;
import com.senifit.was.vo.VideoData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ByPersonalRecommendationHelper {
    private final TsidIdGenerator tsidIdGenerator;

    public Program buildProgram(ProgramData programData, String programHash) {
        Long programId = tsidIdGenerator.nextId();

        return Program.builder()
                .id(programId)
                .name(programData.name())
                .description(programData.description())
                .duration(programData.duration())
                .thumbnailPath(programData.thumbnailPath())
                .warmupWorkoutKind(LookupWorkoutWarmupKind.fromSelection(
                        programData.warmupWorkoutKind()))
                .cooldownWorkoutKind(LookupWorkoutCooldownKind.fromSelection(
                        programData.cooldownWorkoutKind()))
                .cognitiveWorkoutKind(LookupWorkoutCognitiveKind.fromSelection(
                        programData.cognitiveWorkoutKind()))
                .singingWorkoutKind(LookupWorkoutSingingKind.fromSelection(
                        programData.singingWorkoutKind()))
                .specializedWorkoutKind(LookupSpecializedWorkoutKind.fromSelection(
                        programData.specializedWorkoutKind()))
                .primaryTarget(LookupTarget.fromSelection(
                        programData.primaryTarget()))
                .hash(programHash)
                .build();
    }

    public List<ProgramVideo> buildProgramVideos(Long programId, List<VideoData> videoList) {
        List<ProgramVideo> programVideos = new  ArrayList<>();
        int sequenceCount = 1;

        for (VideoData videoData : videoList) {
            programVideos.add(ProgramVideo.builder()
                    .id(new ProgramVideoId(programId, videoData.id()))
                    .sequence(sequenceCount++)
                    .build());
        };

        return programVideos;
    }

    public String generateProgramHash(List<VideoData> videoData) {
        if (videoData == null || videoData.isEmpty()) {
            return "";
        }

        String videoIdsString = videoData.stream()
                .map(VideoData::id)
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(videoIdsString.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            // SHA-256은 표준 알고리즘이므로 예외가 발생하지 않아야 함
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}
