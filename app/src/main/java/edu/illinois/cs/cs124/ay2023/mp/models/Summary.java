package edu.illinois.cs.cs124.ay2023.mp.models;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Model holding the course summary information shown in the summary list.
 *
 * @noinspection unused
 */
public class Summary implements Comparable<Summary> {
  private String subject;

  /**
   * Get the subject for this Summary.
   *
   * @return the subject for this Summary
   */
  @NotNull
  public final String getSubject() {
    return subject;
  }

  private String number;

  /**
   * Get the number for this Summary.
   *
   * @return the number for this Summary
   */
  @NotNull
  public final String getNumber() {
    return number;
  }

  private String label;

  /**
   * Get the label for this Summary.
   *
   * @return the label for this Summary
   */
  @NotNull
  public final String getLabel() {
    return label;
  }

  /** Create an empty Summary. */
  public Summary() {}

  /**
   * Create a Summary with the provided fields.
   *
   * @param setSubject the department for this Summary
   * @param setNumber the number for this Summary
   * @param setLabel the label for this Summary
   */
  public Summary(@NonNull String setSubject, @NonNull String setNumber, @NotNull String setLabel) {
    subject = setSubject;
    number = setNumber;
    label = setLabel;
  }

  /** {@inheritDoc} */
  @NonNull
  @Override
  public String toString() {
    return subject + " " + number + ": " + label;
  }

  @Override
  public int compareTo(Summary summary) {
    if (summary.number.compareTo(number) == 0) {
      return -1 * summary.subject.compareTo(subject);
    }
    return -1 * summary.number.compareTo(number);
  }

  public static List<Summary> filter(List<Summary> list, String filter) {
    List<Summary> newList = new ArrayList<>();
    String word = filter.trim();
    word = word.toLowerCase();
    for (Summary s : list) {
      if (s.toString().toLowerCase().contains(word)) {
        newList.add(s);
      }
    }
    Collections.sort(newList);
    newList.sort(
        (first, second) -> {
          String firstString = first.toString().toLowerCase();
          String secondString = second.toString().toLowerCase();
          String comparedWord = filter.toLowerCase();
          return firstString.indexOf(comparedWord) - secondString.indexOf(comparedWord);
        });
    return newList;
  }
}
