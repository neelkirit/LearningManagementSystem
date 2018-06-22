import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Form } from './form.model';
import { FormService } from './form.service';

@Component({
    selector: 'jhi-form-detail',
    templateUrl: './form-detail.component.html'
})
export class FormDetailComponent implements OnInit, OnDestroy {

    form: Form;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private formService: FormService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInForms();
    }

    load(id) {
        this.formService.find(id)
            .subscribe((formResponse: HttpResponse<Form>) => {
                this.form = formResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInForms() {
        this.eventSubscriber = this.eventManager.subscribe(
            'formListModification',
            (response) => this.load(this.form.id)
        );
    }
}
