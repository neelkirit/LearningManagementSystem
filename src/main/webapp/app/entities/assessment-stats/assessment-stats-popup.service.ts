import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { AssessmentStats } from './assessment-stats.model';
import { AssessmentStatsService } from './assessment-stats.service';

@Injectable()
export class AssessmentStatsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private assessmentStatsService: AssessmentStatsService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.assessmentStatsService.find(id)
                    .subscribe((assessmentStatsResponse: HttpResponse<AssessmentStats>) => {
                        const assessmentStats: AssessmentStats = assessmentStatsResponse.body;
                        this.ngbModalRef = this.assessmentStatsModalRef(component, assessmentStats);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.assessmentStatsModalRef(component, new AssessmentStats());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    assessmentStatsModalRef(component: Component, assessmentStats: AssessmentStats): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.assessmentStats = assessmentStats;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
