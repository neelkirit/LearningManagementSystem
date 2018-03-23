import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Topic } from './topic.model';
import { TopicPopupService } from './topic-popup.service';
import { TopicService } from './topic.service';
import { Course, CourseService } from '../course';

@Component({
    selector: 'jhi-topic-dialog',
    templateUrl: './topic-dialog.component.html'
})
export class TopicDialogComponent implements OnInit {

    topic: Topic;
    isSaving: boolean;

    courses: Course[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private topicService: TopicService,
        private courseService: CourseService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.courseService.query()
            .subscribe((res: HttpResponse<Course[]>) => { this.courses = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.topic.id !== undefined) {
            this.subscribeToSaveResponse(
                this.topicService.update(this.topic));
        } else {
            this.subscribeToSaveResponse(
                this.topicService.create(this.topic));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Topic>>) {
        result.subscribe((res: HttpResponse<Topic>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Topic) {
        this.eventManager.broadcast({ name: 'topicListModification', content: 'OK'});
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
    selector: 'jhi-topic-popup',
    template: ''
})
export class TopicPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private topicPopupService: TopicPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.topicPopupService
                    .open(TopicDialogComponent as Component, params['id']);
            } else {
                this.topicPopupService
                    .open(TopicDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
