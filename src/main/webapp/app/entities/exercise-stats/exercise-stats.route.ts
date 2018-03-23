import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ExerciseStatsComponent } from './exercise-stats.component';
import { ExerciseStatsDetailComponent } from './exercise-stats-detail.component';
import { ExerciseStatsPopupComponent } from './exercise-stats-dialog.component';
import { ExerciseStatsDeletePopupComponent } from './exercise-stats-delete-dialog.component';

@Injectable()
export class ExerciseStatsResolvePagingParams implements Resolve<any> {

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

export const exerciseStatsRoute: Routes = [
    {
        path: 'exercise-stats',
        component: ExerciseStatsComponent,
        resolve: {
            'pagingParams': ExerciseStatsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.exerciseStats.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'exercise-stats/:id',
        component: ExerciseStatsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.exerciseStats.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const exerciseStatsPopupRoute: Routes = [
    {
        path: 'exercise-stats-new',
        component: ExerciseStatsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.exerciseStats.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'exercise-stats/:id/edit',
        component: ExerciseStatsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.exerciseStats.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'exercise-stats/:id/delete',
        component: ExerciseStatsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.exerciseStats.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
