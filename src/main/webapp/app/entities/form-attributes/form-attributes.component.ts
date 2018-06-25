import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FormAttributes } from './form-attributes.model';
import { FormAttributesService } from './form-attributes.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-form-attributes',
    templateUrl: './form-attributes.component.html'
})
export class FormAttributesComponent implements OnInit, OnDestroy {
formAttributes: FormAttributes[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private formAttributesService: FormAttributesService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.formAttributesService.query().subscribe(
            (res: HttpResponse<FormAttributes[]>) => {
                this.formAttributes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFormAttributes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FormAttributes) {
        return item.id;
    }
    registerChangeInFormAttributes() {
        this.eventSubscriber = this.eventManager.subscribe('formAttributesListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
