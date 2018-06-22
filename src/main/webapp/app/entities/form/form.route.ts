import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { FormComponent } from './form.component';
import { FormDetailComponent } from './form-detail.component';
import { FormPopupComponent } from './form-dialog.component';
import { FormDeletePopupComponent } from './form-delete-dialog.component';

export const formRoute: Routes = [
    {
        path: 'form',
        component: FormComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.form.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'form/:id',
        component: FormDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.form.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const formPopupRoute: Routes = [
    {
        path: 'form-new',
        component: FormPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.form.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'form/:id/edit',
        component: FormPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.form.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'form/:id/delete',
        component: FormDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.form.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
