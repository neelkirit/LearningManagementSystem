import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TemplateComponent } from './template.component';
import { TemplateDetailComponent } from './template-detail.component';
import { TemplatePopupComponent } from './template-dialog.component';
import { TemplateDeletePopupComponent } from './template-delete-dialog.component';

@Injectable()
export class TemplateResolvePagingParams implements Resolve<any> {

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

export const templateRoute: Routes = [
    {
        path: 'template',
        component: TemplateComponent,
        resolve: {
            'pagingParams': TemplateResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.template.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'template/:id',
        component: TemplateDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.template.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const templatePopupRoute: Routes = [
    {
        path: 'template-new',
        component: TemplatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.template.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'template/:id/edit',
        component: TemplatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.template.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'template/:id/delete',
        component: TemplateDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.template.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
