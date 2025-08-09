
package com.senifit.was.entity;

import java.util.*;

/**
 * Generated from src/data/global-enum.yml
 * Format: "0xNNNNNNNN: code"
 * DO NOT EDIT MANUALLY.
 */
public enum GlobalEnum {
    WORKOUT(0x10000000L, "workout"),
    WORKOUT_NOT_SELECTED(0x11000000L, "workout.not_selected"),
    WORKOUT_DURATION_30MINUTES(0x12100000L, "workout.duration.30minutes"),
    WORKOUT_DURATION_60MINUTES(0x12200000L, "workout.duration.60minutes"),
    WORKOUT_KINDS_WARMUP(0x13100000L, "workout.kinds.warmup"),
    WORKOUT_KINDS_COOLDOWN(0x13200000L, "workout.kinds.cooldown"),
    WORKOUT_KINDS_COGNITIVE(0x13300000L, "workout.kinds.cognitive"),
    WORKOUT_KINDS_COGNITIVE_KINDS_TAEKWONDO(0x13311000L, "workout.kinds.cognitive.kinds.taekwondo"),
    WORKOUT_KINDS_COGNITIVE_KINDS_DUALTASKING(0x13312000L, "workout.kinds.cognitive.kinds.dualtasking"),
    WORKOUT_KINDS_COGNITIVE_KINDS_CONTINUOUS(0x13313000L, "workout.kinds.cognitive.kinds.continuous"),
    WORKOUT_KINDS_CALISTHENIC(0x13400000L, "workout.kinds.calisthenic"),
    WORKOUT_KINDS_CALISTHENIC_KINDS_UNILATERAL(0x13411000L, "workout.kinds.calisthenic.kinds.unilateral"),
    WORKOUT_KINDS_CALISTHENIC_KINDS_IPSILATERAL(0x13412000L, "workout.kinds.calisthenic.kinds.ipsilateral"),
    WORKOUT_KINDS_CALISTHENIC_TARGETS_ARMS(0x13421000L, "workout.kinds.calisthenic.targets.arms"),
    WORKOUT_KINDS_CALISTHENIC_TARGETS_SHOULDERS(0x13422000L, "workout.kinds.calisthenic.targets.shoulders"),
    WORKOUT_KINDS_CALISTHENIC_TARGETS_ABS(0x13423000L, "workout.kinds.calisthenic.targets.abs"),
    WORKOUT_KINDS_CALISTHENIC_TARGETS_LEGS(0x13424000L, "workout.kinds.calisthenic.targets.legs"),
    WORKOUT_KINDS_CALISTHENIC_TARGETS_BACK(0x13425000L, "workout.kinds.calisthenic.targets.back"),
    WORKOUT_KINDS_CALISTHENIC_TARGETS_ARM(0x13426000L, "workout.kinds.calisthenic.targets.arm"),
    WORKOUT_KINDS_CALISTHENIC_TARGETS_ARMS_AND_SHOULDERS(0x13427000L, "workout.kinds.calisthenic.targets.arms_and_shoulders"),
    WORKOUT_KINDS_SINGING(0x13500000L, "workout.kinds.singing"),
    WORKOUT_PURPOSES_STRENGTH_IMPROVEMENT(0x14100000L, "workout.purposes.strength_improvement"),
    WORKOUT_PURPOSES_COORDINATION_IMPROVEMENT(0x14200000L, "workout.purposes.coordination_improvement"),
    WORKOUT_PURPOSES_MUSCULAR_ENDURANCE_IMPROVEMENT(0x14300000L, "workout.purposes.muscular_endurance_improvement"),
    WORKOUT_PURPOSES_FLEXIBILITY_IMPROVEMENT(0x14400000L, "workout.purposes.flexibility_improvement"),
    WORKOUT_PURPOSES_CARDIOVASCULAR_ENDURANCE_IMPROVEMENT(0x14500000L, "workout.purposes.cardiovascular_endurance_improvement"),
    WORKOUT_PURPOSES_COGNITIVE_AND_DUAL_TASK_ABILITY(0x14600000L, "workout.purposes.cognitive_and_dual_task_ability"),
    WORKOUT_PURPOSES_POSTURE_CORRECTION(0x14700000L, "workout.purposes.posture_correction"),
    WORKOUT_PURPOSES_BALANCE_IMPROVEMENT(0x14800000L, "workout.purposes.balance_improvement"),
    WORKOUT_PURPOSES_FUNCTIONAL_MOVEMENT_IMPROVEMENT(0x14900000L, "workout.purposes.functional_movement_improvement"),
    WORKOUT_PURPOSES_AGILITY_IMPROVEMENT(0x14A00000L, "workout.purposes.agility_improvement"),
    WORKOUT_PURPOSES_BLOOD_SUGAR_PRESSURE_CONTROL(0x14B00000L, "workout.purposes.blood_sugar_pressure_control");

    public final long id;
    public final String code;

    GlobalEnum(long id, String code) {
        this.id = id;
        this.code = code;
    }
}
