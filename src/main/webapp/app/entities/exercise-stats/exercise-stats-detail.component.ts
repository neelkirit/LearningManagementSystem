import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ExerciseStats } from './exercise-stats.model';
import { ExerciseStatsService } from './exercise-stats.service';

@Component({
    selector: 'jhi-exercise-stats-detail',
    templateUrl: './exercise-stats-detail.component.html'
})
export class ExerciseStatsDetailComponent implements OnInit, OnDestroy {

    exerciseStats: ExerciseStats;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private exerciseStatsService: ExerciseStatsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInExerciseStats();
    }

    load(id) {
        this.exerciseStatsService.find(id)
            .subscribe((exerciseStatsResponse: HttpResponse<ExerciseStats>) => {
                this.exerciseStats = exerciseStatsResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInExerciseStats() {
        this.eventSubscriber = this.eventManager.subscribe(
            'exerciseStatsListModification',
            (response) => this.load(this.exerciseStats.id)
        );
    }
}
