import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Assessment } from './assessment.model';
import { AssessmentService } from './assessment.service';

@Component({
    selector: 'jhi-assessment-detail',
    templateUrl: './assessment-detail.component.html'
})
export class AssessmentDetailComponent implements OnInit, OnDestroy {

    assessment: Assessment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private assessmentService: AssessmentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAssessments();
    }

    load(id) {
        this.assessmentService.find(id)
            .subscribe((assessmentResponse: HttpResponse<Assessment>) => {
                this.assessment = assessmentResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAssessments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'assessmentListModification',
            (response) => this.load(this.assessment.id)
        );
    }
}
