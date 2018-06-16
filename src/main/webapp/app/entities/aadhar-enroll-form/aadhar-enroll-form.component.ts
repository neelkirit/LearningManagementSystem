import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AadharEnrollForm } from './aadhar-enroll-form.model';
import { AadharEnrollFormService } from './aadhar-enroll-form.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-aadhar-enroll-form',
    templateUrl: './aadhar-enroll-form.component.html'
})
export class AadharEnrollFormComponent implements OnInit, OnDestroy {
aadharEnrollForms: AadharEnrollForm[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private aadharEnrollFormService: AadharEnrollFormService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.aadharEnrollFormService.query().subscribe(
            (res: HttpResponse<AadharEnrollForm[]>) => {
                this.aadharEnrollForms = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAadharEnrollForms();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AadharEnrollForm) {
        return item.id;
    }
    registerChangeInAadharEnrollForms() {
        this.eventSubscriber = this.eventManager.subscribe('aadharEnrollFormListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
