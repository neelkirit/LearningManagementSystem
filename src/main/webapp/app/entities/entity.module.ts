import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AmoghServerCourseModule } from './course/course.module';
import { AmoghServerTopicModule } from './topic/topic.module';
import { AmoghServerExerciseModule } from './exercise/exercise.module';
import { AmoghServerTemplateModule } from './template/template.module';
import { AmoghServerAssessmentModule } from './assessment/assessment.module';
import { AmoghServerAssessmentStatsModule } from './assessment-stats/assessment-stats.module';
import { AmoghServerExerciseStatsModule } from './exercise-stats/exercise-stats.module';
import { AmoghServerAadharEnrollFormModule } from './aadhar-enroll-form/aadhar-enroll-form.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AmoghServerCourseModule,
        AmoghServerTopicModule,
        AmoghServerExerciseModule,
        AmoghServerTemplateModule,
        AmoghServerAssessmentModule,
        AmoghServerAssessmentStatsModule,
        AmoghServerExerciseStatsModule,
        AmoghServerAadharEnrollFormModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AmoghServerEntityModule {}
