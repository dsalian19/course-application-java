package edu.illinois.cs.cs124.ay2023.mp.models;

import androidx.annotation.NonNull;
import org.jetbrains.annotations.NotNull;

public class Course extends Summary {
  private String description;

  public Course() {}

  public Course(@NonNull String s, @NonNull String n, @NonNull String l, @NonNull String d) {
    super(s, n, l);
    description = d;
  }

  @NotNull
  public final String getDescription() {
    return description;
  }
}
