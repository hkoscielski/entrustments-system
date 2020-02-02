package pl.edu.pwr.psi.entrustmentswebservice.entrustment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Specialists")
@PrimaryKeyJoinColumn(name = "id_course_instructor", referencedColumnName = "id")
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Specialist extends CourseInstructor {

}
