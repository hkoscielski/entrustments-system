package pl.edu.pwr.psi.entrustmentswebservice.studysystem.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.edu.pwr.psi.entrustmentswebservice.entrustment.service.StudyPlanService;
import pl.edu.pwr.psi.entrustmentswebservice.studysystem.service.StudySystemService;

@Component
@Slf4j
public class StudySystemSyncTask {

	@Autowired
	private StudyPlanService studyPlanService;

	@Autowired
	private StudySystemService studySystemService;

	@Scheduled(cron = "${app.study-system.cron.update-course-instructors}")
	public void updateCourseInstructors() {
		log.info("Starting to update course instructors from StudySystem");
		studySystemService.updateCourseInstructors();
		log.info("Course instructors from StudySystem updated successfully!");
	}

	@Scheduled(cron = "${app.study-system.cron.update-study-plans}")
	public void updateStudyPlans() {
		log.info("Starting to update study plans from StudySystem");
		studyPlanService.findAllFaculties().forEach(f -> studySystemService.updateStudyPlans(f.getSymbol()));
		log.info("Study plans from StudySystem updated successfully!");
	}
}
