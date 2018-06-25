import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { FormAttributes } from './form-attributes.model';
import { FormAttributesService } from './form-attributes.service';

@Component({
    selector: 'jhi-form-attributes-detail',
    templateUrl: './form-attributes-detail.component.html'
})
export class FormAttributesDetailComponent implements OnInit, OnDestroy {

    formAttributes: FormAttributes;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private formAttributesService: FormAttributesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFormAttributes();
    }

    load(id) {
        this.formAttributesService.find(id)
            .subscribe((formAttributesResponse: HttpResponse<FormAttributes>) => {
                this.formAttributes = formAttributesResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFormAttributes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'formAttributesListModification',
            (response) => this.load(this.formAttributes.id)
        );
    }
}
