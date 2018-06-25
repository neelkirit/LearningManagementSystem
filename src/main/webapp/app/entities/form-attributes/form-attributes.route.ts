import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { FormAttributesComponent } from './form-attributes.component';
import { FormAttributesDetailComponent } from './form-attributes-detail.component';
import { FormAttributesPopupComponent } from './form-attributes-dialog.component';
import { FormAttributesDeletePopupComponent } from './form-attributes-delete-dialog.component';

export const formAttributesRoute: Routes = [
    {
        path: 'form-attributes',
        component: FormAttributesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.formAttributes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'form-attributes/:id',
        component: FormAttributesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.formAttributes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const formAttributesPopupRoute: Routes = [
    {
        path: 'form-attributes-new',
        component: FormAttributesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.formAttributes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'form-attributes/:id/edit',
        component: FormAttributesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.formAttributes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'form-attributes/:id/delete',
        component: FormAttributesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.formAttributes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
