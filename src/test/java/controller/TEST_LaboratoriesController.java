package controller;

import model.Laboratory;
import model.Student;
import org.junit.Test;
import repository.FileDataPersistence;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class TEST_LaboratoriesController {

    private final LaboratoriesController controller;
    private final FileDataPersistence laboratoryPersistence;

    public TEST_LaboratoriesController() {
        controller = new LaboratoriesController("students.txt", "laboratories.txt");
        laboratoryPersistence = new FileDataPersistence("laboratories.txt");
    }

    @Test
    public void testAddStudent() {
        Student student = new Student("asdf1234", "John Doe", 935);
        assertTrue(controller.saveStudent(student));
    }

    @Test
    public void testAddLaboratory() {
        //Get tomorrow date
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        SimpleDateFormat dateFormate = new SimpleDateFormat("dd/MM/yyyy");
        //Get string format of the tomorrow date
        String dateString = dateFormate.format(date);

        try {
            Laboratory laboratory = new Laboratory(2, dateString, 2, "asdf1234");
            assertTrue(controller.saveLaboratory(laboratory));
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void testAddGrade() {
        try {
            assertTrue(controller.addGrade("asdf1234", "2", 9));
        } catch (IOException | ParseException e) {
            fail();
        }
    }

    @Test
    public void testGetPassingStudents() {
        try {
            List<Student> passedStudents = controller.passedStudents();
            assertNotNull(passedStudents);
            assertFalse(passedStudents.isEmpty());
            for (Student passingStudent : passedStudents) {
                float midGrade = getStudentMidGrade(passingStudent);
                assertTrue(midGrade >= 5);
                System.out.println(passingStudent.getName() + " passed with " + midGrade);
            }

        } catch (IOException | ParseException e) {
            fail();
        }
    }

    private float getStudentMidGrade(Student passingStudent) throws IOException, ParseException {
        List<Laboratory> studentLabs = laboratoryPersistence.getLaboratoryMap().get(passingStudent.getRegNumber());
        float midGrade = studentLabs.get(0).getGrade();
        for (Laboratory laboratory : studentLabs) {
            midGrade = (midGrade + laboratory.getGrade()) / 2;
        }
        return midGrade;
    }
}
