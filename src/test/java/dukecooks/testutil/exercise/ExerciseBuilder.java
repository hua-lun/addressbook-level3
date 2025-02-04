package dukecooks.testutil.exercise;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import dukecooks.model.util.SampleDataUtil;
import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.model.workout.exercise.components.ExerciseName;
import dukecooks.model.workout.exercise.components.Intensity;
import dukecooks.model.workout.exercise.components.MuscleType;
import dukecooks.model.workout.exercise.components.MusclesTrained;
import dukecooks.model.workout.exercise.details.ExerciseDetail;
import dukecooks.model.workout.exercise.details.unit.DistanceUnit;
import dukecooks.model.workout.exercise.details.unit.WeightUnit;

/**
 * A utility class to help with building Person objects.
 */
public class ExerciseBuilder {

    public static final String DEFAULT_NAME = "Bench Press";
    public static final Intensity DEFAULT_INTENSITY = Intensity.MEDIUM;
    public static final MuscleType DEFAULT_PRIMARY_MUSCLE = new MuscleType("Chest");
    public static final MusclesTrained DEFAULT_MUSCLES_TRAINED = new MusclesTrained(DEFAULT_PRIMARY_MUSCLE,
            new ArrayList<MuscleType>());

    private ExerciseName exerciseName;
    private Intensity intensity;
    private MusclesTrained musclesTrained;
    private Set<ExerciseDetail> exerciseDetails;

    public ExerciseBuilder() {
        exerciseName = new ExerciseName(DEFAULT_NAME);
        musclesTrained = DEFAULT_MUSCLES_TRAINED;
        intensity = DEFAULT_INTENSITY;
        exerciseDetails = new HashSet<>();
    }

    /**
     * Initializes the ExerciseBuilder with the data of {@code personToCopy}.
     */
    public ExerciseBuilder(Exercise exerciseToCopy) {
        exerciseName = exerciseToCopy.getExerciseName();
        intensity = exerciseToCopy.getIntensity();
        musclesTrained = exerciseToCopy.getMusclesTrained();
        exerciseDetails = new HashSet<>(exerciseToCopy.getExerciseDetails());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public ExerciseBuilder withName(String name) {
        this.exerciseName = new ExerciseName(name);
        return this;
    }

    /**
     * Sets the {@code MuscleTrained} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withMusclesTrained(MusclesTrained musclesTrained) {
        this.musclesTrained = musclesTrained;
        return this;
    }

    /**
     * Sets the {@code MuscleTrained} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withMusclesTrained(String... muscleTypes) {
        MuscleType primaryMuscle = new MuscleType(muscleTypes[0]);
        ArrayList<MuscleType> secondaryMuscles = new ArrayList<>();
        for (int i = 1; i < muscleTypes.length; i++) {
            MuscleType secondaryMuscle = new MuscleType(muscleTypes[i]);
            secondaryMuscles.add(secondaryMuscle);
        }
        musclesTrained = new MusclesTrained(primaryMuscle, secondaryMuscles);
        return withMusclesTrained(musclesTrained);
    }

    /**
     * Sets the {@code Intensity} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withIntensity(Intensity intensity) {
        this.intensity = intensity;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public ExerciseBuilder withDetails(Float weight, WeightUnit weightUnit, Float distance,
                                       DistanceUnit distanceUnit, Integer reps, Integer sets) {
        this.exerciseDetails = SampleDataUtil.getDetails(weight, weightUnit, distance,
                distanceUnit, reps, sets);
        return this;
    }


    public Exercise build() {
        return new Exercise(exerciseName, musclesTrained, intensity, exerciseDetails);
    }

}
