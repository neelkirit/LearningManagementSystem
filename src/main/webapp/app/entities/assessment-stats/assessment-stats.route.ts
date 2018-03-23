import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AssessmentStatsComponent } from './assessment-stats.component';
import { AssessmentStatsDetailComponent } from './assessment-stats-detail.component';
import { AssessmentStatsPopupComponent } from './assessment-stats-dialog.component';
import { AssessmentStatsDeletePopupComponent } from './assessment-stats-delete-dialog.component';

@Injectable()
export class AssessmentStatsResolvePagingParams implements Resolve<any> {

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

export const assessmentStatsRoute: Routes = [
    {
        path: 'assessment-stats',
        component: AssessmentStatsComponent,
        resolve: {
            'pagingParams': AssessmentStatsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.assessmentStats.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'assessment-stats/:id',
        component: AssessmentStatsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.assessmentStats.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const assessmentStatsPopupRoute: Routes = [
    {
        path: 'assessment-stats-new',
        component: AssessmentStatsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.assessmentStats.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'assessment-stats/:id/edit',
        component: AssessmentStatsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.assessmentStats.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'assessment-stats/:id/delete',
        component: AssessmentStatsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.assessmentStats.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
