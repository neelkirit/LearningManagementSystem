/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { AmoghServerTestModule } from '../../../test.module';
import { AadharEnrollFormDetailComponent } from '../../../../../../main/webapp/app/entities/aadhar-enroll-form/aadhar-enroll-form-detail.component';
import { AadharEnrollFormService } from '../../../../../../main/webapp/app/entities/aadhar-enroll-form/aadhar-enroll-form.service';
import { AadharEnrollForm } from '../../../../../../main/webapp/app/entities/aadhar-enroll-form/aadhar-enroll-form.model';

describe('Component Tests', () => {

    describe('AadharEnrollForm Management Detail Component', () => {
        let comp: AadharEnrollFormDetailComponent;
        let fixture: ComponentFixture<AadharEnrollFormDetailComponent>;
        let service: AadharEnrollFormService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AmoghServerTestModule],
                declarations: [AadharEnrollFormDetailComponent],
                providers: [
                    AadharEnrollFormService
                ]
            })
            .overrideTemplate(AadharEnrollFormDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AadharEnrollFormDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AadharEnrollFormService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new AadharEnrollForm(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.aadharEnrollForm).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
