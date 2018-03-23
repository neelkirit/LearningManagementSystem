import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AssessmentStats } from './assessment-stats.model';
import { AssessmentStatsPopupService } from './assessment-stats-popup.service';
import { AssessmentStatsService } from './assessment-stats.service';
import { User, UserService } from '../../shared';
import { Assessment, AssessmentService } from '../assessment';

@Component({
    selector: 'jhi-assessment-stats-dialog',
    templateUrl: './assessment-stats-dialog.component.html'
})
export class AssessmentStatsDialogComponent implements OnInit {

    assessmentStats: AssessmentStats;
    isSaving: boolean;

    users: User[];

    assessments: Assessment[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private assessmentStatsService: AssessmentStatsService,
        private userService: UserService,
        private assessmentService: AssessmentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.assessmentService.query()
            .subscribe((res: HttpResponse<Assessment[]>) => { this.assessments = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.assessmentStats.id !== undefined) {
            this.subscribeToSaveResponse(
                this.assessmentStatsService.update(this.assessmentStats));
        } else {
            this.subscribeToSaveResponse(
                this.assessmentStatsService.create(this.assessmentStats));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AssessmentStats>>) {
        result.subscribe((res: HttpResponse<AssessmentStats>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: AssessmentStats) {
        this.eventManager.broadcast({ name: 'assessmentStatsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackAssessmentById(index: number, item: Assessment) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-assessment-stats-popup',
    template: ''
})
export class AssessmentStatsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private assessmentStatsPopupService: AssessmentStatsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.assessmentStatsPopupService
                    .open(AssessmentStatsDialogComponent as Component, params['id']);
            } else {
                this.assessmentStatsPopupService
                    .open(AssessmentStatsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
