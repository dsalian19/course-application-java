package edu.illinois.cs.cs124.ay2023.mp.activities;

import android.os.Bundle;
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
import java.util.function.Consumer;

public class CourseActivity extends AppCompatActivity {
  private Course course;
  private Summary summary;
  @Override
  protected void onCreate(@Nullable Bundle unused) {
    super.onCreate(unused);
    setContentView(R.layout.activity_course);
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      summary = objectMapper.readValue(getIntent().getStringExtra("summary"), Summary.class);
      CourseableApplication application = (CourseableApplication) getApplication();
      application.getClient().getCourse(summary, courseCallback);
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

}
