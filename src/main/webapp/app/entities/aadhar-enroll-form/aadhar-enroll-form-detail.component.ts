import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { AadharEnrollForm } from './aadhar-enroll-form.model';
import { AadharEnrollFormService } from './aadhar-enroll-form.service';

@Component({
    selector: 'jhi-aadhar-enroll-form-detail',
    templateUrl: './aadhar-enroll-form-detail.component.html'
})
export class AadharEnrollFormDetailComponent implements OnInit, OnDestroy {

    aadharEnrollForm: AadharEnrollForm;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private aadharEnrollFormService: AadharEnrollFormService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAadharEnrollForms();
    }

    load(id) {
        this.aadharEnrollFormService.find(id)
            .subscribe((aadharEnrollFormResponse: HttpResponse<AadharEnrollForm>) => {
                this.aadharEnrollForm = aadharEnrollFormResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAadharEnrollForms() {
        this.eventSubscriber = this.eventManager.subscribe(
            'aadharEnrollFormListModification',
            (response) => this.load(this.aadharEnrollForm.id)
        );
    }
}
