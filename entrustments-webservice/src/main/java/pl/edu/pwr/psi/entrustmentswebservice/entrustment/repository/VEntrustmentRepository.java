package pl.edu.pwr.psi.entrustmentswebservice.entrustment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.VEntrustment;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity.VEntrustmentId;

import java.util.List;
import java.util.Optional;

@Repository
public interface VEntrustmentRepository extends JpaRepository<VEntrustment, VEntrustmentId> {

	@Query("select ve from VEntrustment ve " +
			"join ve.id.entrustment on ve.id.version = ve.id.entrustment.lastVersion " +
			"where (:academicYear is null or ve.id.entrustment.entrustmentPlan.semester.academicYear = :academicYear) " +
			"and (:semester is null or ve.id.entrustment.entrustmentPlan.semester.semesterNumber = :semester) " +
			"and (:studyLevel is null or ve.id.entrustment.entrustmentPlan.semester.studyPlan.studyLevel.name = :studyLevel) " +
			"and (:specialty is null or ve.id.entrustment.entrustmentPlan.semester.studyPlan.specialty.name = :specialty) " +
			"and (:courseCode is null or ve.course.code = :courseCode)")
	List<VEntrustment> findAllEntrustmentsByPlanAndFilters(
			@Param("academicYear") String academicYear,
			@Param("semester") Integer semester,
			@Param("studyLevel") String studyLevel,
			@Param("specialty") String specialty,
			@Param("courseCode") String courseCode
	);

	Optional<VEntrustment> findByIdEntrustmentIdAndIdVersion(long entrustmentId, int version);

	@Query("select coalesce(sum(ve.numberOfHours), 0) from VEntrustment ve " +
			"join ve.id.entrustment on ve.id.entrustment.lastVersion = ve.id.version " +
			"where ve.courseInstructor.id = :course_instructor_id " +
			"and ve.entrustmentStatus.code <> 'REJECTED'")
	int calculateSumOfEntrustedHoursForCourseInstructor(@Param("course_instructor_id") long courseInstructorId);
}
