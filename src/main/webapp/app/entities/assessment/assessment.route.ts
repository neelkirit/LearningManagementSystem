import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AssessmentComponent } from './assessment.component';
import { AssessmentDetailComponent } from './assessment-detail.component';
import { AssessmentPopupComponent } from './assessment-dialog.component';
import { AssessmentDeletePopupComponent } from './assessment-delete-dialog.component';

@Injectable()
export class AssessmentResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const assessmentRoute: Routes = [
    {
        path: 'assessment',
        component: AssessmentComponent,
        resolve: {
            'pagingParams': AssessmentResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.assessment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'assessment/:id',
        component: AssessmentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.assessment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const assessmentPopupRoute: Routes = [
    {
        path: 'assessment-new',
        component: AssessmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.assessment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'assessment/:id/edit',
        component: AssessmentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.assessment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'assessment/:id/delete',
        component: AssessmentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.assessment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
