import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { ExerciseComponent } from './exercise.component';
import { ExerciseDetailComponent } from './exercise-detail.component';
import { ExercisePopupComponent } from './exercise-dialog.component';
import { ExerciseDeletePopupComponent } from './exercise-delete-dialog.component';

@Injectable()
export class ExerciseResolvePagingParams implements Resolve<any> {

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

export const exerciseRoute: Routes = [
    {
        path: 'exercise',
        component: ExerciseComponent,
        resolve: {
            'pagingParams': ExerciseResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.exercise.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'exercise/:id',
        component: ExerciseDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.exercise.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const exercisePopupRoute: Routes = [
    {
        path: 'exercise-new',
        component: ExercisePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.exercise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'exercise/:id/edit',
        component: ExercisePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.exercise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'exercise/:id/delete',
        component: ExerciseDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.exercise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
