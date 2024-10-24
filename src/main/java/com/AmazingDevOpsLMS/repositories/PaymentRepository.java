package com.AmazingDevOpsLMS.repositories;

import com.AmazingDevOpsLMS.model.Payments;
import com.AmazingDevOpsLMS.model.Program;
import com.AmazingDevOpsLMS.model.Session;
import com.AmazingDevOpsLMS.model.Student;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.MapKeyColumn;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author samuel
 */
public interface PaymentRepository extends CrudRepository<Payments, String> {

    @Query("select p from Payments p where p.student.email=?1 order by p.paymentDate desc")
    public List<Payments> getPaymentsByStudentId(String email);

    @Query("select SUM(p.amount) from Payments p where p.student.email=?1")
    public int getTotalPaymentsByStudentId(String email);

    @Query("select p from Payments p where p.program.Id=?1 and p.student.email=?2")
    public Optional<Payments> findStudentRegisterForSession(String ProgramId, String email);

    @Query("select p.program from Payments p where p.student.email=?1")
    public List<Program> getRegisteredProgramsByStudentId(String email);

    @Query("select p from Payments p where p.id=?1 and p.student.email=?2")
    public Payments findByIdAndUser(String Id, String email);

    @Query("select SUM(p.amount) from Payments p")
    public int getTotalPayments();

    @MapKeyColumn(name = "month")
    @Query("select new Map(SUM(p.amount),MONTH(p.paymentDate) as month) from Payments p group by MONTH(p.paymentDate)")
    public List<Map<Integer, String>> getTotalPaymentsByMonth();

    @Query("select p from Payments p where MONTH(p.paymentDate)=?1 and YEAR(p.paymentDate)=?2")
    public List<Payments> getAllPaymentsByMonth(int month, int year);

    @Query("select distinct(YEAR(p.paymentDate)) from Payments p")
    public List<String> paymentYears();

    @Query("select distinct(MONTH(p.paymentDate)) from Payments p")
    public List<String> paymentMonthByYear();

    @Query("select p.student from Payments p where MONTH(p.paymentDate)=?1 and YEAR(p.paymentDate)=?2 and p.program.Id=?3")
    public List<Student> getEnrollmentByMonth(int month, int year, String program);
    
    @MapKeyColumn(name = "program")
    @Query("select new Map(count(p.program.Id) as count , p.program.name as program) from Payments p where YEAR(p.paymentDate)=?1 group by p.program.Id")
    public List<Map<Integer, String>> getEnrollmentByProgramAndYear(int year);
    @MapKeyColumn(name = "year")
    @Query("select new Map(count(p.student) as course_enrollments, YEAR(p.paymentDate) as year) from Payments p group by YEAR(p.paymentDate) order by YEAR(p.paymentDate) ASC")
    public List<Map<Integer, String>> getEnrollmentGroupedbyYear();
}
