import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AadharEnrollFormComponent } from './aadhar-enroll-form.component';
import { AadharEnrollFormDetailComponent } from './aadhar-enroll-form-detail.component';
import { AadharEnrollFormPopupComponent } from './aadhar-enroll-form-dialog.component';
import { AadharEnrollFormDeletePopupComponent } from './aadhar-enroll-form-delete-dialog.component';

export const aadharEnrollFormRoute: Routes = [
    {
        path: 'aadhar-enroll-form',
        component: AadharEnrollFormComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.aadharEnrollForm.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'aadhar-enroll-form/:id',
        component: AadharEnrollFormDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.aadharEnrollForm.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const aadharEnrollFormPopupRoute: Routes = [
    {
        path: 'aadhar-enroll-form-new',
        component: AadharEnrollFormPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.aadharEnrollForm.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'aadhar-enroll-form/:id/edit',
        component: AadharEnrollFormPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.aadharEnrollForm.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'aadhar-enroll-form/:id/delete',
        component: AadharEnrollFormDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'amoghServerApp.aadharEnrollForm.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
