import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { AssessmentStats } from './assessment-stats.model';
import { AssessmentStatsService } from './assessment-stats.service';

@Component({
    selector: 'jhi-assessment-stats-detail',
    templateUrl: './assessment-stats-detail.component.html'
})
export class AssessmentStatsDetailComponent implements OnInit, OnDestroy {

    assessmentStats: AssessmentStats;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private assessmentStatsService: AssessmentStatsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAssessmentStats();
    }

    load(id) {
        this.assessmentStatsService.find(id)
            .subscribe((assessmentStatsResponse: HttpResponse<AssessmentStats>) => {
                this.assessmentStats = assessmentStatsResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAssessmentStats() {
        this.eventSubscriber = this.eventManager.subscribe(
            'assessmentStatsListModification',
            (response) => this.load(this.assessmentStats.id)
        );
    }
}
