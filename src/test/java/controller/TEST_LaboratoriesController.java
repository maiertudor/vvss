package controller;

import model.Laboratory;
import model.Student;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class TEST_LaboratoriesController {

    private final LaboratoriesController controller;

    public TEST_LaboratoriesController() {
        controller = new LaboratoriesController("students.txt", "laboratories.txt");
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
            assertTrue(false);
        }
    }
}
