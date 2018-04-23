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
    private final FileDataPersistence studentPersistence;

    public TEST_LaboratoriesController() {
        controller = new LaboratoriesController("students.txt", "laboratories.txt");
        laboratoryPersistence = new FileDataPersistence("laboratories.txt");
        studentPersistence = new FileDataPersistence("students.txt");
    }

    @Test
    public void testAddStudent() throws IOException {
        int initialSize = studentPersistence.getStudentsList().size();
        //EC 1 8 12
        Student student = new Student("asdf1234", "John Doe", 935);
        assertTrue(controller.saveStudent(student));
        assertEquals(studentPersistence.getStudentsList().size(), initialSize + 1);
        //EC 2
        student.setRegNumber("asdf12345");
        assertFalse(controller.saveStudent(student));
        assertEquals(studentPersistence.getStudentsList().size(), initialSize + 1);
        //EC 3
        student.setRegNumber("asdf123");
        assertFalse(controller.saveStudent(student));
        assertEquals(studentPersistence.getStudentsList().size(), initialSize + 1);
        //EC 4
        student.setRegNumber("1234asdb");
        assertFalse(controller.saveStudent(student));
        assertEquals(studentPersistence.getStudentsList().size(), initialSize + 1);
        //EC 5
        student.setRegNumber("asdfe1234");
        assertFalse(controller.saveStudent(student));
        assertEquals(studentPersistence.getStudentsList().size(), initialSize + 1);
        //EC 6
        student.setRegNumber("asd1234");
        assertFalse(controller.saveStudent(student));
        assertEquals(studentPersistence.getStudentsList().size(), initialSize + 1);
        //EC 7
        student.setRegNumber("");
        assertFalse(controller.saveStudent(student));
        assertEquals(studentPersistence.getStudentsList().size(), initialSize + 1);

        student.setRegNumber("asdf1234");
        //EC 9
        student.setGroup(-1);
        assertFalse(controller.saveStudent(student));
        assertEquals(studentPersistence.getStudentsList().size(), initialSize + 1);
        //EC 10
        student.setGroup(1000);
        assertFalse(controller.saveStudent(student));
        assertEquals(studentPersistence.getStudentsList().size(), initialSize + 1);

        student.setGroup(935);
        //EC 13
        student.setName("John---Doe");
        assertFalse(controller.saveStudent(student));
        assertEquals(studentPersistence.getStudentsList().size(), initialSize + 1);
        //EC 14
        student.setName("");
        assertFalse(controller.saveStudent(student));
        assertEquals(studentPersistence.getStudentsList().size(), initialSize + 1);
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

        c.set(2010,1,1);
        Date dateInPast = c.getTime();
        try {
            Laboratory laboratory = new Laboratory(2, dateString, 2, "asdf1234");
            assertTrue(controller.saveLaboratory(laboratory));
            laboratory.setNumber(-2);
            assertFalse(controller.saveLaboratory(laboratory));
            laboratory.setNumber(2);
            laboratory.setProblemNumber(11);
            assertFalse(controller.saveLaboratory(laboratory));
            laboratory.setProblemNumber(2);
            laboratory.setDate(dateInPast);
            assertFalse(controller.saveLaboratory(laboratory));
            laboratory.setDate(date);
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void testAddGrade() {
        try {
            assertTrue(controller.addGrade("asdf1234", "2", 5));
            assertFalse(controller.addGrade("asdf1234", "2", 11));
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
