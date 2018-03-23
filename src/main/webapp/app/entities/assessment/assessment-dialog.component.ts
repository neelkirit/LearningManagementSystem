import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Assessment } from './assessment.model';
import { AssessmentPopupService } from './assessment-popup.service';
import { AssessmentService } from './assessment.service';
import { Course, CourseService } from '../course';

@Component({
    selector: 'jhi-assessment-dialog',
    templateUrl: './assessment-dialog.component.html'
})
export class AssessmentDialogComponent implements OnInit {

    assessment: Assessment;
    isSaving: boolean;

    courses: Course[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private assessmentService: AssessmentService,
        private courseService: CourseService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.courseService
            .query({filter: 'assessment-is-null'})
            .subscribe((res: HttpResponse<Course[]>) => {
                if (!this.assessment.courseId) {
                    this.courses = res.body;
                } else {
                    this.courseService
                        .find(this.assessment.courseId)
                        .subscribe((subRes: HttpResponse<Course>) => {
                            this.courses = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.assessment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.assessmentService.update(this.assessment));
        } else {
            this.subscribeToSaveResponse(
                this.assessmentService.create(this.assessment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Assessment>>) {
        result.subscribe((res: HttpResponse<Assessment>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Assessment) {
        this.eventManager.broadcast({ name: 'assessmentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCourseById(index: number, item: Course) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-assessment-popup',
    template: ''
})
export class AssessmentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private assessmentPopupService: AssessmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.assessmentPopupService
                    .open(AssessmentDialogComponent as Component, params['id']);
            } else {
                this.assessmentPopupService
                    .open(AssessmentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
