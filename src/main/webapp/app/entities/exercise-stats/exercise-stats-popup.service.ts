import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ExerciseStats } from './exercise-stats.model';
import { ExerciseStatsService } from './exercise-stats.service';

@Injectable()
export class ExerciseStatsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private exerciseStatsService: ExerciseStatsService

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
                this.exerciseStatsService.find(id)
                    .subscribe((exerciseStatsResponse: HttpResponse<ExerciseStats>) => {
                        const exerciseStats: ExerciseStats = exerciseStatsResponse.body;
                        this.ngbModalRef = this.exerciseStatsModalRef(component, exerciseStats);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.exerciseStatsModalRef(component, new ExerciseStats());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    exerciseStatsModalRef(component: Component, exerciseStats: ExerciseStats): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.exerciseStats = exerciseStats;
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
