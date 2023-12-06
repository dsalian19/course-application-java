package edu.illinois.cs.cs124.ay2023.mp.activities;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.illinois.cs.cs124.ay2023.mp.R;
import edu.illinois.cs.cs124.ay2023.mp.application.CourseableApplication;
import edu.illinois.cs.cs124.ay2023.mp.helpers.ResultMightThrow;
import edu.illinois.cs.cs124.ay2023.mp.models.Summary;
import edu.illinois.cs.cs124.ay2023.mp.models.Course;
import edu.illinois.cs.cs124.ay2023.mp.models.Rating;
import java.util.function.Consumer;

public class CourseActivity extends AppCompatActivity
    implements RatingBar.OnRatingBarChangeListener {
  private Course course;
  private Rating rating;
  private Summary summary;
  @Override
  protected void onCreate(@Nullable Bundle unused) {
    super.onCreate(unused);
    setContentView(R.layout.activity_course);
    RatingBar ratingView = findViewById(R.id.rating);
    ratingView.setOnRatingBarChangeListener(this);
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      summary = objectMapper.readValue(getIntent().getStringExtra("summary"), Summary.class);
      CourseableApplication application = (CourseableApplication) getApplication();
      application.getClient().getCourse(summary, courseCallback);
      application.getClient().getRating(summary, ratingCallback);
    } catch (JsonProcessingException e) {
      throw new RuntimeException();
    }
  }
  private final Consumer<ResultMightThrow<Course>> courseCallback = (result) -> {
    course = result.getValue();
    TextView descriptionTextView = findViewById(R.id.description);
    runOnUiThread(() -> {
      descriptionTextView.setText(summary.toString() + " " + course.getDescription());
    });
  };
  private final Consumer<ResultMightThrow<Rating>> ratingCallback =
      (result) -> {
        try {
          rating = result.getValue();
          runOnUiThread(() -> {
            RatingBar ratingBar = findViewById(R.id.rating);
            ratingBar.setRating(rating.getRating());
          });
        } catch (Exception e) {
          e.printStackTrace();
        }
        //indent
      };
  @Override
  public void onRatingChanged(RatingBar ratingBar, float ratingValue, boolean fromUser) {
    CourseableApplication app = (CourseableApplication) getApplication();
    Rating newRating = new Rating(rating.getSummary(), ratingValue);
    app.getClient().postRating(newRating, ratingCallback);
    app.getClient().getRating(summary, ratingCallback);
  }
}
