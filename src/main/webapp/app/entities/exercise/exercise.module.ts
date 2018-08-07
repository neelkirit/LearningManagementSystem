import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AmoghServerSharedModule } from '../../shared';
import {
    ExerciseService,
    ExercisePopupService,
    ExerciseComponent,
    ExerciseDetailComponent,
    ExerciseDialogComponent,
    ExercisePopupComponent,
    ExerciseUploadComponent,
    ExerciseUploadPopupComponent,
    ExerciseDeletePopupComponent,
    ExerciseDeleteDialogComponent,
    exerciseRoute,
    exercisePopupRoute,
    ExerciseResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...exerciseRoute,
    ...exercisePopupRoute,
];

@NgModule({
    imports: [
        AmoghServerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ExerciseComponent,
        ExerciseDetailComponent,
        ExerciseDialogComponent,
        ExerciseUploadComponent,
        ExerciseDeleteDialogComponent,
        ExercisePopupComponent,
        ExerciseUploadPopupComponent,
        ExerciseDeletePopupComponent,
    ],
    entryComponents: [
        ExerciseComponent,
        ExerciseDialogComponent,
        ExerciseUploadComponent,
        ExercisePopupComponent,
        ExerciseUploadPopupComponent,
        ExerciseDeleteDialogComponent,
        ExerciseDeletePopupComponent,
    ],
    providers: [
        ExerciseService,
        ExercisePopupService,
        ExerciseResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AmoghServerExerciseModule {}
