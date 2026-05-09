package controllers;
//waed rabah zaqout

import dao.CourseDAO;
import dao.EnrollmentDAO;
import dao.StudentDAO;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Enrollment;

public class EnrollmentController implements Initializable {

    @FXML
    private ComboBox<Integer> studentsCombobox;

    @FXML
    private ComboBox<Integer> coursesCombobox;

    @FXML
    private DatePicker enrollmentDate;

    @FXML
    private TableView<Enrollment> table;

    @FXML
    private TableColumn<Enrollment, Integer> enrollmentIdTC;

    @FXML
    private TableColumn<Enrollment, Integer> studentIdTC;

    @FXML
    private TableColumn<Enrollment, Integer> courseIdTC;

    @FXML
    private TableColumn<Enrollment, String> enrollmentDateTC;

    StudentDAO studentdao = new StudentDAO();
    CourseDAO coursedao = new CourseDAO();
    EnrollmentDAO enrollmentdao = new EnrollmentDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        enrollmentIdTC.setCellValueFactory(
                new PropertyValueFactory<>("enrollmentId"));

        studentIdTC.setCellValueFactory(
                new PropertyValueFactory<>("studentId"));

        courseIdTC.setCellValueFactory(
                new PropertyValueFactory<>("courseId"));

        enrollmentDateTC.setCellValueFactory(
                new PropertyValueFactory<>("enrollmentDate"));

        List<Integer> studentIds = studentdao.getAllStudentsIds();
        studentsCombobox.getItems().addAll(studentIds);

        List<Integer> courseIds = coursedao.getAllCoursesIds();
        coursesCombobox.getItems().addAll(courseIds);

        table.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {

                    if (newValue == null) {
                        return;
                    }

                    studentsCombobox.setValue(newValue.getStudentId());

                    coursesCombobox.setValue(newValue.getCourseId());

                    enrollmentDate.setValue(
                            LocalDate.parse(newValue.getEnrollmentDate()));
                }
        );
    }

    @FXML
    private void viewHandle(ActionEvent event) {

        List<Enrollment> enrollments = enrollmentdao.findAll();

        table.getItems().setAll(enrollments);
    }

    @FXML
    private void addHandle(ActionEvent event) {

        if (enrollmentValidator()) {

            Integer studentId = studentsCombobox.getValue();

            Integer courseId = coursesCombobox.getValue();

            String date = enrollmentDate.getValue().toString();

            if (enrollmentdao.exists(studentId, courseId)) {

                showWarningAlert(
                        "Duplicate Enrollment",
                        "Enrollment Already Exists",
                        "This student is already enrolled in this course"
                );

                return;
            }

            Enrollment e =
                    new Enrollment(studentId, courseId, date);

            boolean success = enrollmentdao.insertOne(e);

            if (success) {

                clear();

                viewHandle(event);

                showInfoAlert(
                        "Success",
                        "Enrollment added successfully"
                );
            }

        } else {

            showWarningAlert(
                    "Invalid Input",
                    "Missing Data",
                    "Please select student, course and enrollment date"
            );
        }
    }

    @FXML
    private void updateHandle(ActionEvent event) {

        Enrollment selected =
                table.getSelectionModel().getSelectedItem();

        if (selected == null) {

            showWarningAlert(
                    "No Selection",
                    "No Record Selected",
                    "Please select an enrollment record from the table"
            );

            return;
        }

        if (!enrollmentValidator()) {

            showWarningAlert(
                    "Invalid Input",
                    "Missing Data",
                    "Please select student, course and enrollment date"
            );

            return;
        }

        selected.setStudentId(studentsCombobox.getValue());

        selected.setCourseId(coursesCombobox.getValue());

        selected.setEnrollmentDate(
                enrollmentDate.getValue().toString());

        boolean success = enrollmentdao.updateOne(selected);

        if (success) {

            clear();

            viewHandle(event);

            showInfoAlert(
                    "Success",
                    "Enrollment updated successfully"
            );
        }
    }

    @FXML
    private void deleteHandle(ActionEvent event) {

        Enrollment selected =
                table.getSelectionModel().getSelectedItem();

        if (selected == null) {

            showWarningAlert(
                    "No Selection",
                    "No Record Selected",
                    "Please select an enrollment record from the table"
            );

            return;
        }

        if (showConfirmationAlert(
                "Delete Confirmation",
                "Are you sure?",
                "Do you want to delete this enrollment record?"
        )) {

            boolean success =
                    enrollmentdao.deleteOne(selected);

            if (success) {

                clear();

                viewHandle(event);

                showInfoAlert(
                        "Success",
                        "Enrollment deleted successfully"
                );
            }
        }
    }

    private boolean enrollmentValidator() {

        if (studentsCombobox.getValue() == null
                || coursesCombobox.getValue() == null
                || enrollmentDate.getValue() == null) {

            return false;
        }

        return true;
    }

    private void clear() {

        studentsCombobox.setValue(null);

        coursesCombobox.setValue(null);

        enrollmentDate.setValue(null);
    }

    private void showWarningAlert(
            String title,
            String header,
            String message
    ) {

        Alert alert =
                new Alert(Alert.AlertType.WARNING);

        alert.setTitle(title);

        alert.setHeaderText(header);

        alert.setContentText(message);

        alert.showAndWait();
    }

    private void showInfoAlert(
            String title,
            String message
    ) {

        Alert alert =
                new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(title);

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();
    }

    private boolean showConfirmationAlert(
            String title,
            String header,
            String message
    ) {

        Alert alert =
                new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle(title);

        alert.setHeaderText(header);

        alert.setContentText(message);

        Optional<ButtonType> result =
                alert.showAndWait();

        return result.isPresent()
                && result.get() == ButtonType.OK;
    }
}